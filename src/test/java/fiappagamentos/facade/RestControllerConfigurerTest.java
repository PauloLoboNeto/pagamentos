package fiappagamentos.facade;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static fiappagamentos.util.PagamentoHelper.gerarPagamento;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestControllerConfigurerTest {

    private RestControllerConfigurer config = new RestControllerConfigurer();

    @Test
    void deveConfigurarRestTemplate() {
        var restTemplate = config.restTemplate();
        assertThat(restTemplate).isNotNull();
        assertThat(restTemplate).isInstanceOf(RestTemplate.class);
    }

}
