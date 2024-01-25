package fiappagamentos.exceptions.entities;

public class PagamentoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PagamentoInvalidoException() {
        super("Pagamento inválido");
    }
}
