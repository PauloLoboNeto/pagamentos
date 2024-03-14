package fiappagamentos.gateways;

import fiappagamentos.interfaces.gateways.INotificaClienteQueuePort;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificaClienteQueueAdapter implements INotificaClienteQueuePort {

    private final SnsTemplate snsTemplate;

    @Value("${queue.notifica.cliente}")
    private String queueNotificaCliente;

    @Override
    public void publish(String msg) {
        snsTemplate.sendNotification(queueNotificaCliente, msg, "subject");
    }
}