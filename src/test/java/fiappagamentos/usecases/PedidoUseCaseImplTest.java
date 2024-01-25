package fiappagamentos.usecases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.interfaces.gateways.IPedidoHttpPort;
import fiappagamentos.interfaces.usecases.IPedidoUseCasePort;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;

class PedidoUseCaseImplTest {

    private IPedidoUseCasePort pedidoUseCase;

    @Mock
    private IPedidoHttpPort pedidoHttpPort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoUseCase = new PedidoUseCaseImpl(pedidoHttpPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class AtualizarStatus {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Atualizar status do pedido")
        void deveAtualizarStatus_IdPedidoInformado() {
            when(pedidoHttpPort.atualizarStatus(any(UUID.class))).thenReturn(true);

            var pedidoAtualizado = pedidoUseCase.atualizarStatus(UUID.randomUUID());

            assertThat(pedidoAtualizado).isTrue();
            verify(pedidoHttpPort, times(1)).atualizarStatus(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Gera excecao quando atualizar status do pedido e nao informar o id do pedido")
        void deveGerarExcecao_QuandoAtualizarStatus_IdPedidoNaoInformado() {
            assertThatThrownBy(() -> pedidoUseCase.atualizarStatus(null)).isInstanceOf(PedidoInvalidoException.class)
                    .hasMessage("Pedido inválido");

            verify(pedidoHttpPort, never()).atualizarStatus(any(UUID.class));
        }
    }
}
