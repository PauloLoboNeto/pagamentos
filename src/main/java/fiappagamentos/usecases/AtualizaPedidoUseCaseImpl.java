package fiappagamentos.usecases;

import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import fiappagamentos.interfaces.usecases.IAtualizaPedidoUseCasePort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AtualizaPedidoUseCaseImpl implements IAtualizaPedidoUseCasePort {

    private final IAtualizaPedidoQueuePort atualizaPedidoQueuePort;
    @Override
    public void atualizaPedido(UUID idPedido) {
        atualizaPedidoQueuePort.publish(pedidoToJson(idPedido));
    }

    public String pedidoToJson(UUID idPedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"idPedido\": \""+idPedido+"\", ");
        sb.append("\"tipoAtualizacao\": \"P\"}");
        return sb.toString();
    }

}