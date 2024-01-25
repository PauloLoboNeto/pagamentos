package fiappagamentos.usecases;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.utils.enums.StatusPagamento;
import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.exceptions.entities.PedidoUseCaseInvalidoException;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import fiappagamentos.interfaces.usecases.IPedidoUseCasePort;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PagamentoUseCaseImpl implements IPagamentoUseCasePort {

    private final IPagamentoRepositoryPort pagamentoRepositoryPort;

    @Override
    public Pagamento realizarPagamento(UUID idPedido, IPedidoUseCasePort pedidoUseCasePort)
            throws PedidoInvalidoException, PedidoUseCaseInvalidoException {
        if (Objects.isNull(idPedido)) {
            throw new PedidoInvalidoException();
        }
        if (Objects.isNull(pedidoUseCasePort)) {
            throw new PedidoUseCaseInvalidoException();
        }
        Optional<Pagamento> pagamentoDB = localizarPorPedido(idPedido);
        Pagamento pagamento = null;
        if (pagamentoDB.isPresent()) {
            pagamentoDB.get().setStatusPagamento(StatusPagamento.APROVADO);
            pagamento = pagamentoRepositoryPort.atualizar(pagamentoDB.get());
        } else {
            pagamento = pagamentoRepositoryPort.atualizar(new Pagamento(null, idPedido, StatusPagamento.APROVADO));
        }

        boolean pedidoAtualizado = pedidoUseCasePort.atualizarStatus(idPedido);

        return Objects.nonNull(pagamento) && Objects.nonNull(pedidoAtualizado) ? pagamento : null;
    }

    @Override
    public Pagamento recuzarPagamento(UUID idPedido) throws PedidoInvalidoException {
        if (Objects.isNull(idPedido)) {
            throw new PedidoInvalidoException();
        }
        Optional<Pagamento> pagamentoDB = localizarPorPedido(idPedido);
        if (pagamentoDB.isPresent()) {
            pagamentoDB.get().setStatusPagamento(StatusPagamento.RECUSADO);
            Pagamento pagamento = pagamentoRepositoryPort.atualizar(pagamentoDB.get());
            return pagamento;
        }

        Pagamento pagamento = pagamentoRepositoryPort
                .atualizar(new Pagamento(null, idPedido, StatusPagamento.RECUSADO));
        return pagamento;
    }

    @Override
    public Optional<Pagamento> localizarPorPedido(UUID idPedido) {
        return pagamentoRepositoryPort.localizarPorPedido(idPedido);
    }

}
