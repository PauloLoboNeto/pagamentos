package fiappagamentos.exceptions.handlers;

import fiappagamentos.controllers.PagamentoController;
import fiappagamentos.exceptions.entities.PedidoInvalidoException;
import fiappagamentos.exceptions.entities.PedidoNaoEncontradoException;
import fiappagamentos.interfaces.usecases.IPagamentoUseCasePort;
import fiappagamentos.interfaces.usecases.IPedidoUseCasePort;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PagamentoExceptinHandlerTest {

    private PagamentoExceptionHandler handler = new PagamentoExceptionHandler();

    @Test
    void deveGerarExcecao_QuandoPedidoInvalido() throws Exception {
        var exception = new PedidoInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.pedidoInvalido(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void deveGerarExcecao_QuandoPedidoNaoForEncontrado() throws Exception {
        var exception = new PedidoNaoEncontradoException();
        var req = new MockHttpServletRequest();
        var res = handler.pedidoNaoEncontrado(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
