package fiappagamentos.controllers;

import fiappagamentos.adapters.PagamentoDTO;
import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;
import fiappagamentos.interfaces.usecases.IAtualizaPedidoUseCasePort;
import fiappagamentos.interfaces.usecases.INotificaClienteUseCasePort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PagamentoControllerTest {

    private MockMvc mockMvc;
    @Mock
    private IPagamentoUseCasePort pagamentoUseCase;
    @Mock
    private IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort;
    @Mock
    private INotificaClienteUseCasePort notificaClienteUseCasePort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        PagamentoController pagamentoController = new PagamentoController(pagamentoUseCase, atualizaPedidoUseCasePort, notificaClienteUseCasePort);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RealizarPagamento {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Realizar pagamento")
        void deveRealizarPagamento() throws Exception {
            var pagamento = gerarPagamento();
            when(pagamentoUseCase.realizarPagamento(any(UUID.class), any(IAtualizaPedidoUseCasePort.class), any(INotificaClienteUseCasePort.class))).thenReturn(pagamento);

            mockMvc.perform(post("/{idPedido}", UUID.randomUUID()))
                    .andExpect(status().isOk());

            verify(pagamentoUseCase, times(1)).realizarPagamento(any(UUID.class),any(IAtualizaPedidoUseCasePort.class), any(INotificaClienteUseCasePort.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar excecao ao realizar pagamento, pedido nulo")
        void deveGerarExcecao_QuandoRealizarPagamento_PedidoNulo() throws Exception {
            var pagamento = gerarPagamento();
            when(pagamentoUseCase.realizarPagamento(any(UUID.class), any(IAtualizaPedidoUseCasePort.class), any(INotificaClienteUseCasePort.class))).thenReturn(null);

            mockMvc.perform(post("/{idPedido}", UUID.randomUUID()))
                    .andExpect(status().isInternalServerError());

            verify(pagamentoUseCase, times(1)).realizarPagamento(any(UUID.class),any(IAtualizaPedidoUseCasePort.class), any(INotificaClienteUseCasePort.class));
        }
    }

    @Nested
    class LocalizarPagamentoDoPedido {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Localizar pagamento do pedido")
        void deveLocalizarPagamentoDoPedido() throws Exception{
            var pagamento = gerarPagamento();
            var pagamentoDTO = new PagamentoDTO(pagamento);

            when(pagamentoUseCase.localizarPorPedido(any(UUID.class))).thenReturn(Optional.of(pagamento));
            mockMvc.perform(get("/{idPedido}", UUID.randomUUID()))
                    .andExpect(status().isOk());

            verify(pagamentoUseCase, times(1)).localizarPorPedido(any(UUID.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar excecao ao localizar pagamento do pedido quando pagamento nao for localizado")
        void deveGerarExcecao_QuandoLocalizarPagamentoDoPedido_PagamentoNaoEncontrado() throws Exception {
            var pagamento = gerarPagamento();
            var pagamentoDTO = new PagamentoDTO(pagamento);

            when(pagamentoUseCase.localizarPorPedido(any(UUID.class))).thenReturn(Optional.empty());
            mockMvc.perform(get("/{idPedido}", UUID.randomUUID()))
                    .andExpect(status().isNotFound());

            verify(pagamentoUseCase, times(1)).localizarPorPedido(any(UUID.class));
        }
    }


}