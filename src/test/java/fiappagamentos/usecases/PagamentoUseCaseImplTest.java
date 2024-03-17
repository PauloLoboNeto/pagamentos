package fiappagamentos.usecases;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.exceptions.entities.PedidoUseCaseInvalidoException;
import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;
import fiappagamentos.interfaces.usecases.IAtualizaPedidoUseCasePort;
import fiappagamentos.interfaces.usecases.INotificaClienteUseCasePort;
import fiappagamentos.utils.enums.StatusPagamento;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;

class PagamentoUseCaseImplTest {

    private IPagamentoUseCasePort pagamentoUseCase;

    @Mock
    private IPagamentoRepositoryPort pagamentoRepositoryPort;

    @Mock
    private IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort;
    @Mock
    private INotificaClienteUseCasePort notificaClienteUseCasePort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pagamentoUseCase = new PagamentoUseCaseImpl(pagamentoRepositoryPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RealizarPagamento {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Realizar um pagamento quando localizar id do pedido")
        void deveRealizarPagamento_IdPedidoLocalizado() {
            var pagamento = gerarPagamento();
            pagamento.setStatusPagamento(StatusPagamento.APROVADO);

            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.of(pagamento));
            when(pagamentoRepositoryPort.atualizar(any(Pagamento.class))).thenReturn(pagamento);

            var pagamentoRealizado = pagamentoUseCase.realizarPagamento(UUID.randomUUID(), atualizaPedidoUseCasePort, notificaClienteUseCasePort);

            assertThat(pagamentoRealizado).isNotNull();
            assertThat(pagamentoRealizado.getStatusPagamento()).isEqualTo(StatusPagamento.APROVADO);

            verify(pagamentoRepositoryPort, times(1)).localizarPorPedido(any(UUID.class));
            verify(pagamentoRepositoryPort, times(1)).atualizar(any(Pagamento.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Realizar um pagamento quando nao localizar id do pedido")
        void deveRealizarPagamento_IdPedidoNaoLocalizado() {
            var pagamento = gerarPagamento();
            pagamento.setStatusPagamento(StatusPagamento.APROVADO);

            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.empty());
            when(pagamentoRepositoryPort.atualizar(any(Pagamento.class))).thenReturn(pagamento);

            var pagamentoRealizado = pagamentoUseCase.realizarPagamento(UUID.randomUUID(), atualizaPedidoUseCasePort, notificaClienteUseCasePort);

            assertThat(pagamentoRealizado).isNotNull();
            assertThat(pagamentoRealizado.getStatusPagamento()).isEqualTo(StatusPagamento.APROVADO);

            verify(pagamentoRepositoryPort, times(1)).localizarPorPedido(any(UUID.class));
            verify(pagamentoRepositoryPort, times(1)).atualizar(any(Pagamento.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar uma excecao ao realizar um pagamento quando o id do pedido for nulo")
        void deveGerarExcecao_QuandoRealizarPagamento_IdPedidoNulo() {
            assertThatThrownBy(() -> pagamentoUseCase.realizarPagamento(null, atualizaPedidoUseCasePort, notificaClienteUseCasePort))
                    .isInstanceOf(PedidoInvalidoException.class)
                    .hasMessage("Pedido inválido");

            verify(pagamentoRepositoryPort, never()).localizarPorPedido(any(UUID.class));
            verify(pagamentoRepositoryPort, never()).atualizar(any(Pagamento.class));
            verify(notificaClienteUseCasePort, never()).notificaCliente(any(UUID.class));
            verify(atualizaPedidoUseCasePort, never()).atualizaPedido(any(UUID.class));
        }

//        @Test
//        @Severity(SeverityLevel.CRITICAL)
//        @Description("Gerar uma excecao ao realizar um pagamento quando a dependencia de usecase para pedido nao for informada")
//        void deveGerarExcecao_QuandoRealizarPagamento_PedidoUseCaseNulo() {
//            assertThatThrownBy(() -> pagamentoUseCase.realizarPagamento(UUID.randomUUID(), atualizaPedidoUseCasePort, notificaClienteUseCasePort))
//                    .isInstanceOf(PedidoUseCaseInvalidoException.class)
//                    .hasMessage("Dependencia usecase de pedido invalida");
//
//            verify(pagamentoRepositoryPort, never()).localizarPorPedido(any(UUID.class));
//            verify(pagamentoRepositoryPort, never()).atualizar(any(Pagamento.class));
//            verify(notificaClienteUseCasePort, never()).notificaCliente(any(UUID.class));
//            verify(atualizaPedidoUseCasePort, never()).atualizaPedido(any(UUID.class));
//        }
    }

    @Nested
    class RecusarPagamento {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Recusar um pagamento quando o localizar o id do pedido")
        void deveRecusarPagamento_IdPedidoLocalizado() {
            var pagamento = gerarPagamento();
            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.of(pagamento));
            when(pagamentoRepositoryPort.atualizar(any(Pagamento.class))).thenReturn(pagamento);

            var pagamentoRecusado = pagamentoUseCase.recuzarPagamento(UUID.randomUUID(), atualizaPedidoUseCasePort, notificaClienteUseCasePort);

            assertThat(pagamentoRecusado).isNotNull();
            assertThat(pagamentoRecusado).isEqualTo(pagamento);
            verify(pagamentoRepositoryPort, times(1)).localizarPorPedido(any(UUID.class));
            verify(pagamentoRepositoryPort, times(1)).atualizar(any(Pagamento.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Recusar um pagamento quando nao localizar o id do pedido")
        void deveRecusarPagamento_IdPedidoNaoLocalizado() {
            var pagamento = gerarPagamento();
            pagamento.setStatusPagamento(StatusPagamento.RECUSADO);

            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.empty());
            when(pagamentoRepositoryPort.atualizar(any(Pagamento.class))).thenReturn(pagamento);

            var pagamentoRecusado = pagamentoUseCase.recuzarPagamento(UUID.randomUUID(), atualizaPedidoUseCasePort, notificaClienteUseCasePort);

            assertThat(pagamentoRecusado).isNotNull();
            assertThat(pagamentoRecusado.getStatusPagamento()).isEqualTo(StatusPagamento.RECUSADO);

            verify(pagamentoRepositoryPort, times(1)).localizarPorPedido(any(UUID.class));
            verify(pagamentoRepositoryPort, times(1)).atualizar(any(Pagamento.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Recusar um pagamento quando nao informar o id do pedido")
        void deveRecusarPagamento_IdPedidoNaoInformado() {
            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> pagamentoUseCase.recuzarPagamento(null, atualizaPedidoUseCasePort, notificaClienteUseCasePort))
                    .isInstanceOf(PedidoInvalidoException.class).hasMessage("Pedido inválido");

            verify(pagamentoRepositoryPort, never()).localizarPorPedido(any(UUID.class));
            verify(pagamentoRepositoryPort, never()).atualizar(any(Pagamento.class));
        }
    }

    @Nested
    class LocalizarPagamento {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Localizar um pagamento atravez do id do pedido")
        void deveLocalizarPagamentoPorIdPedido() {
            var pagamento = gerarPagamento();
            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.of(pagamento));

            var pagamentoLocalizado = pagamentoUseCase.localizarPorPedido(UUID.randomUUID());

            assertThat(pagamentoLocalizado).isPresent();
            assertThat(pagamentoLocalizado.get()).isEqualTo(pagamento);
            verify(pagamentoRepositoryPort, times(1)).localizarPorPedido(any(UUID.class));

        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Retornar optional vazio quando Localizar um pagamento e o id do pedido for nulo")
        void deveRetornarOptionalVazio_QuandoLocalizarPagamentoPorIdPedido_IdPedidoNulo() {
            when(pagamentoRepositoryPort.localizarPorPedido(any(UUID.class))).thenReturn(Optional.empty());

            var pagamentoLocalizado = pagamentoUseCase.localizarPorPedido(UUID.randomUUID());
            assertThat(pagamentoLocalizado).isEmpty();
            verify(pagamentoRepositoryPort, times(1)).localizarPorPedido(any(UUID.class));
        }
    }

}