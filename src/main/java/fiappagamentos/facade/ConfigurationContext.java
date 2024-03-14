package fiappagamentos.facade;

import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.usecases.IAtualizaPedidoUseCasePort;
import fiappagamentos.interfaces.usecases.INotificaClienteUseCasePort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import fiappagamentos.usecases.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationContext {

    @Bean
    public IPagamentoUseCasePort pagamentoUseCase(IPagamentoRepositoryPort pagamentoRepositoryPort) {
        return new PagamentoUseCaseImpl(pagamentoRepositoryPort);
    }

    @Bean
    public INotificaClienteUseCasePort notificaClienteUseCase(INotificaClienteQueuePort notificaClienteQueuePort) {
        return new NotificaClienteUseCaseImpl(notificaClienteQueuePort);
    }

    @Bean
    public IAtualizaPedidoUseCasePort atualizaPedidoUseCase(IAtualizaPedidoQueuePort atualizaPedidoQueuePort) {
        return new AtualizaPedidoUseCaseImpl(atualizaPedidoQueuePort);
    }

}