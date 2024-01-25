package fiappagamentos.interfaces.usecases;

import fiappagamentos.exceptions.entities.PedidoNaoEncontradoException;

import java.util.UUID;

public interface IPedidoUseCasePort {
    boolean atualizarStatus(UUID idPedido) throws PedidoNaoEncontradoException;

}
