package fiappagamentos.usecases;

import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;
import fiappagamentos.interfaces.usecases.INotificaClienteUseCasePort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class NotificaClienteUseCaseImpl implements INotificaClienteUseCasePort {

    private final INotificaClienteQueuePort notificaClienteQueuePort;
    @Override
    public void notificaCliente(UUID idPedido) {
        notificaClienteQueuePort.publish(pedidoToJson(idPedido));
    }

    public String pedidoToJson(UUID idPedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"idPedido\": \""+idPedido+"\", ");
        sb.append("\"tipoAtualizacao\": \"P\"}");
        return sb.toString();
    }
}