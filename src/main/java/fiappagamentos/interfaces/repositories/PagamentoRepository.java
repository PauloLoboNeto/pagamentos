package fiappagamentos.interfaces.repositories;

import fiappagamentos.gateways.entities.PagamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//@Repository
public interface PagamentoRepository extends MongoRepository<PagamentoEntity, String> {

    Optional<PagamentoEntity> findAllByIdPedido(UUID idPedido);

}
