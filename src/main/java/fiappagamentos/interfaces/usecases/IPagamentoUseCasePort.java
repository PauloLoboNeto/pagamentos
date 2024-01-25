package fiappagamentos.interfaces.usecases;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PedidoInvalidoException;

import java.util.Optional;
import java.util.UUID;

public interface IPagamentoUseCasePort {
    Pagamento realizarPagamento(UUID idPedido, IPedidoUseCasePort pedidoUseCasePort) throws PedidoInvalidoException;
    Pagamento recuzarPagamento(UUID idPedido) throws PedidoInvalidoException;
    Optional<Pagamento> localizarPorPedido(UUID idPedido);
}
