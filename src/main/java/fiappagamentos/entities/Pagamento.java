package fiappagamentos.entities;


import fiappagamentos.utils.enums.StatusPagamento;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Pagamento {
    private String id;
    private UUID idPedido;
    private StatusPagamento statusPagamento;

}
