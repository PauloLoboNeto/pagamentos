package fiappagamentos.controllers;

import fiappagamentos.adapters.PagamentoDTO;
import fiappagamentos.entities.Pagamento;
import fiappagamentos.interfaces.usecases.IAtualizaPedidoUseCasePort;
import fiappagamentos.interfaces.usecases.INotificaClienteUseCasePort;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PagamentoController {

    private final IPagamentoUseCasePort pagamentoUseCase;
    private final IAtualizaPedidoUseCasePort atualizaPedidoUseCasePort;
    private final INotificaClienteUseCasePort notificaClienteUseCasePort;

    @PostMapping("/{idPedido}")
    public ResponseEntity<?> realizaPagamento(@PathVariable(name = "idPedido", required = true) UUID idPedido) {
        Pagamento res = pagamentoUseCase.realizarPagamento(idPedido, atualizaPedidoUseCasePort, notificaClienteUseCasePort);

        if (Objects.isNull(res)) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(new PagamentoDTO(res));
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<?> localizarPagamentoDoPedido(@PathVariable(name = "idPedido", required = true) UUID idPedido) {
        Optional<Pagamento> pagamento = pagamentoUseCase.localizarPorPedido(idPedido);
        if (pagamento.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok(new PagamentoDTO(pagamento.get()));
    }

}