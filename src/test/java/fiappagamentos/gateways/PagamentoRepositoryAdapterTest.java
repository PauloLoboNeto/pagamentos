package fiappagamentos.gateways;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PagamentoInvalidoException;
import fiappagamentos.gateways.entities.PagamentoEntity;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.repositories.PagamentoRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;

class PagamentoRepositoryAdapterTest {

    private IPagamentoRepositoryPort pagamentoRepositoryAdapter;

    @Mock
    private PagamentoRepository pagamentoRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pagamentoRepositoryAdapter = new PagamentoRepositoryAdapter(pagamentoRepository);

    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class AtualizarPagamento {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Atualizar pagamento")
        void deveAtualizarPagamento() {
            var pagamento = gerarPagamento();
            var pagamentoEntity = new PagamentoEntity().from(pagamento);

            when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

            var pagamentoAtualizado = pagamentoRepositoryAdapter.atualizar(pagamento);

            assertThat(pagamentoAtualizado).isNotNull();
            assertThat(pagamentoAtualizado).isInstanceOf(Pagamento.class);
            assertThat(pagamentoAtualizado).isEqualTo(pagamento);

            verify(pagamentoRepository, times(1)).save(any(PagamentoEntity.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar excecao quando tentar atualizar pagamento e paramento pagamento for nulo")
        void deveGerarExcecao_QuandoAtualizarPagamento_PagamentoNulo() {
            assertThatThrownBy(() -> pagamentoRepositoryAdapter.atualizar(null))
                    .isInstanceOf(PagamentoInvalidoException.class)
                    .hasMessage("Pagamento inválido");

            verify(pagamentoRepository, never()).save(any(PagamentoEntity.class));
        }
    }

    @Nested
    class LocaliarPagamentoPorPedido {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Localizar pagamento atraves do id do pedido")
        void deveLocalizarPagamentoPeloIdPedido() {
            var pagamento = gerarPagamento();
            var pagamentoEntity = new PagamentoEntity().from(pagamento);

            when(pagamentoRepository.findAllByIdPedido(any(UUID.class))).thenReturn(Optional.of(pagamentoEntity));

            var pagamentoLocalizado = pagamentoRepositoryAdapter.localizarPorPedido(UUID.randomUUID());

            assertThat(pagamentoLocalizado).isPresent();
            assertThat(pagamentoLocalizado).isNotNull();
            assertThat(pagamentoLocalizado.get()).isEqualTo(pagamento);
            verify(pagamentoRepository, times(1)).findAllByIdPedido(any(UUID.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Localizar pagamento atraves do id do pedido nao localizado")
        void deveLocalizarPagamentoPeloIdPedido_IdPedidoNaoLocalizado() {
            when(pagamentoRepository.findAllByIdPedido(any(UUID.class))).thenReturn(Optional.empty());

            var pagamentoLocalizado = pagamentoRepositoryAdapter.localizarPorPedido(UUID.randomUUID());

            assertThat(pagamentoLocalizado).isEmpty();
            verify(pagamentoRepository, times(1)).findAllByIdPedido(any(UUID.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar excecao ao localizar pagamento atraves do id do pedido quando id do pedido for nulo")
        void deveGerarExcecao_QuandoLocalizarPagamentoPeloIdPedido_IdPedidoNulo() {
            assertThatThrownBy(() -> pagamentoRepositoryAdapter.localizarPorPedido(null))
                    .isInstanceOf(PagamentoInvalidoException.class)
                    .hasMessage("Pagamento inválido");

            verify(pagamentoRepository, never()).findAllByIdPedido(any(UUID.class));
        }
    }

}
