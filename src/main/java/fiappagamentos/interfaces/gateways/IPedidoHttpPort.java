package fiappagamentos.interfaces.gateways;

import java.util.UUID;

import fiappagamentos.exceptions.entities.PedidoNaoEncontradoException;

public interface IPedidoHttpPort {
    boolean atualizarStatus(UUID idPedido) throws PedidoNaoEncontradoException;

}
