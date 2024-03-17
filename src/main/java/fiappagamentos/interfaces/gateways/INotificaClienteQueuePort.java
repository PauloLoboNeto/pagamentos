package fiappagamentos.interfaces.gateways;

public interface INotificaClienteQueuePort {
    void publish(String message);
}
