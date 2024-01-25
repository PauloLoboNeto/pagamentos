package fiappagamentos.gateways.entities;

import fiappagamentos.entities.Pagamento;
import fiappagamentos.utils.enums.StatusPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("pagamentos")
public class PagamentoEntity {
    @Id
    private String id;

    private UUID idPedido;

    @NotNull
    private StatusPagamento status;

    public Pagamento to() {
        return Pagamento.builder()
                .id(this.id)
                .idPedido(this.idPedido)
                .statusPagamento(this.status)
            .build();
    }

    public PagamentoEntity from(Pagamento pagamento) {
        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .idPedido(pagamento.getIdPedido())
                .status(pagamento.getStatusPagamento())
            .build();
    }
}
