package br.com.ambev;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Teste de contexto
 */
@SpringBootTest
@ActiveProfiles("h2")
class PedidoApplicationTests {
	
	@Test
	void contextLoads() {
		
	}

}
