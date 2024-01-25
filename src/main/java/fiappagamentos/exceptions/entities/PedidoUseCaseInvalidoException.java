package fiappagamentos.exceptions.entities;

public class PedidoUseCaseInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoUseCaseInvalidoException() {
        super("Dependencia usecase de pedido invalida");
    }
}
