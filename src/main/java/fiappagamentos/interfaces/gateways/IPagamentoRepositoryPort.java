package fiappagamentos.interfaces.gateways;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PagamentoInvalidoException;

import java.util.Optional;
import java.util.UUID;

public interface IPagamentoRepositoryPort {
    Pagamento atualizar(Pagamento pagamento) throws PagamentoInvalidoException;
    Optional<Pagamento> localizarPorPedido(UUID idPedido);
}
