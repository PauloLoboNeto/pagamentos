package fiappagamentos.util;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.utils.enums.StatusPagamento;

import java.util.UUID;

public abstract class PagamentoHelper {
    public static Pagamento gerarPagamento() {
        return Pagamento.builder()
                .statusPagamento(StatusPagamento.PENDENTE)
                .idPedido(UUID.randomUUID())
                .id("635981f6e40f61599e000064")
                .build();
    }
}
