package fiappagamentos.facade;

import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.gateways.IPedidoHttpPort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import fiappagamentos.interfaces.usecases.IPedidoUseCasePort;
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
    public IPedidoUseCasePort pedidoUseCasePort(IPedidoHttpPort pedidoHttpPort) {
        return new PedidoUseCaseImpl(pedidoHttpPort);
    }

}
