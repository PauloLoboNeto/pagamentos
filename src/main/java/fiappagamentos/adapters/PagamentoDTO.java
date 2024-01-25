package fiappagamentos.adapters;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.utils.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PagamentoDTO {
    private UUID pedido;
    private StatusPagamento status;
    private String menssagem;

    public PagamentoDTO(Pagamento pagamento) {
        this.pedido = pagamento.getIdPedido();
        this.status = pagamento.getStatusPagamento();
        this.menssagem = pagamento.getStatusPagamento().getDescricao();
    }
}
