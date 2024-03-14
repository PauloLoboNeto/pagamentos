package fiappagamentos.usecases;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.exceptions.entities.PedidoUseCaseInvalidoException;
import fiappagamentos.interfaces.gateways.IPagamentoRepositoryPort;
import fiappagamentos.interfaces.usecases.IAtualizaPedidoUseCasePort;
import fiappagamentos.interfaces.usecases.INotificaClienteUseCasePort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import fiappagamentos.utils.enums.StatusPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PagamentoUseCaseImpl implements IPagamentoUseCasePort {

    private final IPagamentoRepositoryPort pagamentoRepositoryPort;
    @Override
    @Transactional
    public Pagamento realizarPagamento(UUID idPedido, IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort, INotificaClienteUseCasePort notificaClienteUseCasePort)
            throws PedidoInvalidoException, PedidoUseCaseInvalidoException {
        if (Objects.isNull(idPedido)) {
            throw new PedidoInvalidoException();
        }

        Optional<Pagamento> pagamentoDB = localizarPorPedido(idPedido);
        Pagamento pagamento = null;
        if (pagamentoDB.isPresent()) {
            pagamentoDB.get().setStatusPagamento(StatusPagamento.APROVADO);
            pagamento = pagamentoRepositoryPort.atualizar(pagamentoDB.get());
        } else {
            pagamento = pagamentoRepositoryPort.atualizar(new Pagamento(null, idPedido, StatusPagamento.APROVADO));
        }

        atualizaPedidoUseCasePort.atualizaPedido(idPedido);
        notificaClienteUseCasePort.notificaCliente(idPedido);

        return Objects.nonNull(pagamento) ? pagamento : null;
    }

    @Override
    @Transactional
    public Pagamento recuzarPagamento(UUID idPedido, IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort, INotificaClienteUseCasePort notificaClienteUseCasePort) throws PedidoInvalidoException {
        if (Objects.isNull(idPedido)) {
            throw new PedidoInvalidoException();
        }
        Optional<Pagamento> pagamentoDB = localizarPorPedido(idPedido);
        if (pagamentoDB.isPresent()) {
            pagamentoDB.get().setStatusPagamento(StatusPagamento.RECUSADO);
            Pagamento pagamento = pagamentoRepositoryPort.atualizar(pagamentoDB.get());
            atualizaPedidoUseCasePort.atualizaPedido(idPedido);
            notificaClienteUseCasePort.notificaCliente(idPedido);
            return pagamento;
        }

        Pagamento pagamento = pagamentoRepositoryPort
                .atualizar(new Pagamento(null, idPedido, StatusPagamento.RECUSADO));
        atualizaPedidoUseCasePort.atualizaPedido(idPedido);
        notificaClienteUseCasePort.notificaCliente(idPedido);
        return pagamento;
    }

    @Override
    public Optional<Pagamento> localizarPorPedido(UUID idPedido) {
        return pagamentoRepositoryPort.localizarPorPedido(idPedido);
    }

}