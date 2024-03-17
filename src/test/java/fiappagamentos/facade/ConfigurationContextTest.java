package fiappagamentos.facade;

import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigurationContextTest {

    private ConfigurationContext config = new ConfigurationContext();

    @Mock
    private IPagamentoRepositoryPort pagamentoRepositoryPort;
    @Mock
    private INotificaClienteQueuePort notificaClienteQueuePort;
    @Mock
    private IAtualizaPedidoQueuePort atualizaPedidoQueuePort;

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
//    @Test
//    void deveConfigurarNoficaClienteUseCase() {
//        var notificaClienteUseCase = config.notificaClienteUseCase(notificaClienteQueuePort);
//        assertThat(notificaClienteUseCase).isNotNull();
//        assertThat(notificaClienteUseCase).isInstanceOf(INotificaClienteQueuePort.class);
//    }
//    @Test
//    void deveConfigurarAtualizaPedidoUseCase() {
//        var atualizaPedioUseCase = config.atualizaPedidoUseCase(atualizaPedidoQueuePort);
//        assertThat(atualizaPedioUseCase).isNotNull();
//        assertThat(atualizaPedioUseCase).isInstanceOf(IAtualizaPedidoQueuePort.class);
//    }

}