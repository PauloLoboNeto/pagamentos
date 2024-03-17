package fiappagamentos.interfaces.usecases;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;

import java.util.Optional;
import java.util.UUID;

public interface IPagamentoUseCasePort {
    Optional<Pagamento> localizarPorPedido(UUID idPedido);
    Pagamento realizarPagamento(UUID idPedido, IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort, INotificaClienteUseCasePort notificaClienteUseCasePort) throws PedidoInvalidoException;
    Pagamento recuzarPagamento(UUID idPedido, IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort, INotificaClienteUseCasePort notificaClienteUseCasePort) throws PedidoInvalidoException;
}
