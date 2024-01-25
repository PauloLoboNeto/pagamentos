package fiappagamentos.gateways;

import java.util.Objects;
import java.util.UUID;

import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fiappagamentos.exceptions.entities.PedidoNaoEncontradoException;
import fiappagamentos.interfaces.gateways.IPedidoHttpPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoHttpAdapter implements IPedidoHttpPort {

    private final RestTemplate restTemplate;

    @Value("${pedido.service.url}")
    private String urlServicePedido;

    @Override
    public boolean atualizarStatus(UUID idPedido) throws PedidoInvalidoException, PedidoNaoEncontradoException {
        if (Objects.isNull(idPedido)) {
            throw new PedidoInvalidoException();
        }
        StringBuilder urlRequsicao = new StringBuilder();
        urlRequsicao.append(urlServicePedido);
        urlRequsicao.append("/" + idPedido);
        urlRequsicao.append("/webhook");

        var response = restTemplate.exchange(urlRequsicao.toString(), HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new PedidoNaoEncontradoException();
        }
        return response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

}
