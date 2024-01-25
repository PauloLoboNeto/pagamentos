package fiappagamentos.gateways;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PagamentoInvalidoException;
import fiappagamentos.gateways.entities.PagamentoEntity;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.repositories.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoRepositoryAdapter implements IPagamentoRepositoryPort {

    private final PagamentoRepository pagamentoRepository;

    @Override
    @Transactional
    public Pagamento atualizar(Pagamento pagamento) throws PagamentoInvalidoException {
        if (Objects.isNull(pagamento)) {
            throw new PagamentoInvalidoException();
        }
        PagamentoEntity pagamentoEntity = new PagamentoEntity().from(pagamento);
        return this.pagamentoRepository.save(pagamentoEntity).to();
    }

    @Override
    public Optional<Pagamento> localizarPorPedido(UUID idPedido) throws PagamentoInvalidoException {
        if (Objects.isNull(idPedido)) {
            throw new PagamentoInvalidoException();
        }
        return this.pagamentoRepository.findAllByIdPedido(idPedido).map(e -> e.to());
    }
}
