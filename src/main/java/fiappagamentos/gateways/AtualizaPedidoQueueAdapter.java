package fiappagamentos.gateways;

import fiappagamentos.interfaces.gateways.IAtualizaPedidoQueuePort;
import org.springframework.stereotype.Service;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AtualizaPedidoQueueAdapter implements IAtualizaPedidoQueuePort {
    @Value("${queue.atualiza.pedido}")
    private String queueAtualizaPedido;
    @Override
    public void publish(String message) {
        final AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion("us-east-1").build();
        String queueStr = sqs.getQueueUrl(queueAtualizaPedido).getQueueUrl();
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueStr)
                .withMessageBody(message)
                .withDelaySeconds(5);
        sqs.sendMessage(sendMessageRequest);
    }
}