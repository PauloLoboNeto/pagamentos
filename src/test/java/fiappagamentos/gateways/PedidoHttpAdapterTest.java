package fiappagamentos.gateways;

import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.exceptions.entities.PedidoNaoEncontradoException;
import fiappagamentos.interfaces.gateways.IPedidoHttpPort;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;

class PedidoHttpAdapterTest {

    private IPedidoHttpPort pedidoHttpAdapter;
    @Mock
    private RestTemplate restTemplate;

    AutoCloseable mock;
    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoHttpAdapter = new PedidoHttpAdapter(restTemplate);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class AtualizarStatusPedido {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Atualizar status do pedido")
        void deveAtualizarStatusPedido() {
            when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                    .thenReturn(new ResponseEntity<>("Requisição recebida e processamento iniciado.", HttpStatus.OK));

            var retorno = pedidoHttpAdapter.atualizarStatus(UUID.randomUUID());

            assertThat(retorno).isTrue();
            verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar excecao ao atualizar status do pedido id nulo")
        void deveGerarExcecao_QuandoAtualizarStatusPedido_IdPedidoNulo() {
            assertThatThrownBy(() -> pedidoHttpAdapter.atualizarStatus(null))
                .isInstanceOf(PedidoInvalidoException.class)
                .hasMessage("Pedido inválido");

            verify(restTemplate, never()).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar excecao ao atualizar status do pedido, pedido nao encontrado")
        void deveGerarExcecao_QuandoAtualizarStatusPedido_PedidoNaoLocalizado() {
            when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                    .thenReturn(new ResponseEntity<>("Requisição recebida e processamento iniciado.", HttpStatus.NOT_FOUND));

            assertThatThrownBy(() -> pedidoHttpAdapter.atualizarStatus(UUID.randomUUID()))
                    .isInstanceOf(PedidoNaoEncontradoException.class)
                    .hasMessage("Pedido não encontrado");

            verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class));
        }
    }

}
