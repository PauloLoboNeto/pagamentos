package fiappagamentos.interfaces.gateways;

public interface IAtualizaPedidoQueuePort {
    void publish(String message);
}
