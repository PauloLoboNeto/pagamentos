package fiappagamentos.facade;

import fiappagamentos.gateways.PagamentoRepositoryAdapter;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.gateways.IPedidoHttpPort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import fiappagamentos.interfaces.usecases.IPedidoUseCasePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ConfigurationContextTest {

    private ConfigurationContext config = new ConfigurationContext();

    @Mock
    private IPagamentoRepositoryPort pagamentoRepositoryPort;
    @Mock
    private IPedidoHttpPort pedidoHttpPort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveConfigurarPagamentoUseCase() {
        var pagamentoUseCase = config.pagamentoUseCase(pagamentoRepositoryPort);
        assertThat(pagamentoUseCase).isNotNull();
        assertThat(pagamentoUseCase).isInstanceOf(IPagamentoUseCasePort.class);
    }
    @Test
    void deveConfigurarPedidoUseCase() {
        var pedidoUseCase = config.pedidoUseCasePort(pedidoHttpPort);
        assertThat(pedidoUseCase).isNotNull();
        assertThat(pedidoUseCase).isInstanceOf(IPedidoUseCasePort.class);
    }
}
