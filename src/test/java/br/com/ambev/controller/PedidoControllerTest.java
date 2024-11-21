package br.com.ambev.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ambev.dto.PedidoControllerInputDTO;
import br.com.ambev.dto.PedidoControllerOutputDTO;
import br.com.ambev.dto.PedidoProdutoInputDTO;
import br.com.ambev.entity.Pedido;
import br.com.ambev.exception.BusinessException;
import br.com.ambev.exception.PedidoMensagemException;
import br.com.ambev.repository.PedidoRepository;
import br.com.ambev.service.PedidoService;
import br.com.ambev.util.ConverterService;
import jakarta.transaction.Transactional;

/* Teste de funcionalidades do {@link PedidoController} para gerenciamento e consulta de pedidos */
@TestPropertySource("/application.yml")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ConverterService converterService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoRepository pedidoRepository;

	String baseUrl = "http://localhost:8080";
	
	@BeforeEach
	public void setup() throws BusinessException {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Cenário:valida o gerenciamento de um pedido realizado com sucesso. <br>
	 * Dado um pedido com dados válidos <br>
	 * Quando feito uma chamada para o serviço de gerenciamento de pedidos <br>
	 * Então deve retornar os dados do pedido e status de sucesso <br>
	 * E deve ter cadastrado o pedido e calculado o valor total dos produtos
	 */
	@Test
	@Order(1)
	void validaGerenciamentoDePedidosComSucesso() throws Exception {
		// Dado
		var inputDTO = dadoUmPedidoComDadosValidos();
		// Quando
		var resultado = quandoFeitoUmaChamadaServicoGerenciamentoPedidos(inputDTO);
		// Então
		var outputDTO = entaoDeveRetornarComSucesso(resultado);
		// E
		deveTerCadastradoPedidoComValorTotal(outputDTO);

	}

	/**
	 * Cenário: valida o controle de pedidos duplicados.<br>
	 * Dado um pedido com dados duplicados </br>
	 * Quando feito uma chamada para o serviço de gerenciamento de pedidos <br>
	 * Então deve retornar erro "PEDIDO_DUPLICADO"
	 */
	@Test
	@Order(2)
	void validaGerenciamentoDePedidosComErroDePedidoDuplicado() throws Exception {
		// Dado
		var inputDTO = dadoUmPedidoDuplicado();
		// Quando
		var resultado = quandoFeitoUmaChamadaServicoGerenciamentoPedidos(inputDTO);
		// Então
		entaoDeveRetornarErroPedidoDuplicado(resultado, PedidoMensagemException.PEDIDO_DUPLICADO);
	}

	/**
	 * Cenário:valida o sistema de consulta de um pedido realizado com sucesso. <br>
	 * Dado um código de pedido com dados válidos <br>
	 * Quando feito uma chamada para o serviço de consulta de pedidos <br>
	 * Então deve retornar os dados do pedido e status de sucesso <br>
	 * E deve ter retornar dados do pedido
	 */
	@Test
	@Order(3)
	void validaSistemaConsultaDePedidosComSucesso() throws Exception {
		// Dado
		var codigoPedido = dadoUmCodigoPedidoComDadosValidos();
		// Quando
		var resultado = quandoFeitoUmaChamadaServicoConsultaPedidos(codigoPedido);
		// Então
		var outputDTO = entaoDeveRetornarComSucesso(resultado);
		// E
		deveTerRetornadoDadosDoPedido(outputDTO);

	}

	/**
	 * Cenário: valida o sistema de consulta de pedidos com pedidos não
	 * encontrados.<br>
	 * Dado um pedido inexistente </br>
	 * Quando feito uma chamada para o serviço de consulta de pedidos <br>
	 * Então deve retornar erro "PEDIDO_NAO_ENCONTRADO"
	 */
	@Test
	@Order(4)
	void validaSistemaConsultaPedidoComPedidoNaoExistente() throws Exception {
		// Dado
		var codigoPedido = dadoUmPedidoInexistente();
		// Quando
		var resultado = quandoFeitoUmaChamadaServicoConsultaPedidos(codigoPedido);
		// Então
		entaoDeveRetornarErroPedidoNaoEncontrado(resultado, PedidoMensagemException.PEDIDO_NAO_ENCONTRADO);
	}

	// Métodos usados nos testes
	private PedidoControllerInputDTO dadoUmPedidoComDadosValidos() {
		Set<PedidoProdutoInputDTO> listaPedidoProduto = new HashSet<PedidoProdutoInputDTO>();
		listaPedidoProduto.add(PedidoProdutoInputDTO.builder().codProduto(1L).nomeProduto("CERVEJA")
				.quantidadeProduto(1L).valorUnidade(new BigDecimal("10.00")).build());
		return PedidoControllerInputDTO.builder().codCliente(1L).listaPedidoProduto(listaPedidoProduto).build();

	}

	private ResultActions quandoFeitoUmaChamadaServicoGerenciamentoPedidos(PedidoControllerInputDTO inputDTO)
			throws Exception {
		String content = converterService.converterParaJson(inputDTO);
		RequestBuilder requestBuilder = post(baseUrl+PedidoController.path)// Url
				.content(content)// Conteudo
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		// Submete
		var resultado = mockMvc.perform(requestBuilder);
		return resultado;
	}

	private PedidoControllerOutputDTO entaoDeveRetornarComSucesso(ResultActions resultado) throws Exception {
		MvcResult mvcResult = resultado.andDo(print()).andExpect(status().isOk()).andReturn();
		String json = mvcResult.getResponse().getContentAsString();
		var outputDTO = objectMapper.readValue(json, PedidoControllerOutputDTO.class);
		// Retorno do serviço com sucesso
		assertThat(outputDTO).isNotNull();
		assertThat(outputDTO.getCodPedido()).isNotNull();
		assertThat(outputDTO.getDataPedido()).isNotNull();
		assertThat(outputDTO.getStatusPedido()).isNotNull();
		assertThat(outputDTO.getValorPedido()).isNotNull();
		assertThat(outputDTO.getListaProdutos()).isNotNull();
		return outputDTO;
	}

	private void deveTerCadastradoPedidoComValorTotal(PedidoControllerOutputDTO outputDTO) {
		Optional<Pedido> pedido = pedidoRepository.findById(outputDTO.getCodPedido());
		assertThat(pedido).isNotNull();
		assertThat(pedido.get().getCodPedido()).isNotNull();
		assertThat(pedido.get().getDataPedido()).isNotNull();
		assertThat(pedido.get().getStatusPedido()).isNotNull();
		assertThat(pedido.get().getPedidoProduto()).isNotNull();
		assertThat(pedido.get().getValorTotalPedido()).isNotNull();

	}

	private PedidoControllerInputDTO dadoUmPedidoDuplicado() throws BusinessException {
		Set<PedidoProdutoInputDTO> listaPedidoProduto = new HashSet<PedidoProdutoInputDTO>();
		listaPedidoProduto.add(PedidoProdutoInputDTO.builder()
							.codProduto(1L)
							.nomeProduto("CERVEJA")
							.quantidadeProduto(1L)
							.valorUnidade(new BigDecimal("10.00"))
							.build());
		PedidoControllerInputDTO pedidoControllerInputDTO = PedidoControllerInputDTO.builder()
				.codCliente(1L)
				.listaPedidoProduto(listaPedidoProduto)
				.build();
		pedidoService.gerenciaPedido(pedidoControllerInputDTO);
		return pedidoControllerInputDTO;
	}

	private Long dadoUmPedidoInexistente() {
		Long codigoPedido = 2L;
		return codigoPedido;
	}

	private ResultActions quandoFeitoUmaChamadaServicoConsultaPedidos(Long codigoPedido) throws Exception {
		RequestBuilder requestBuilder = get(baseUrl+PedidoController.path + "/" + codigoPedido)// Url
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		// Submete
		var resultado = mockMvc.perform(requestBuilder);
		return resultado;
	}

	private Long dadoUmCodigoPedidoComDadosValidos() throws BusinessException {
		Set<PedidoProdutoInputDTO> listaPedidoProduto = new HashSet<PedidoProdutoInputDTO>();
		listaPedidoProduto.add(PedidoProdutoInputDTO.builder()
							.codProduto(1L)
							.nomeProduto("CERVEJA")
							.quantidadeProduto(1L)
							.valorUnidade(new BigDecimal("10.00"))
							.build());
		PedidoControllerInputDTO pedidoControllerInputDTO = PedidoControllerInputDTO.builder()
				.codCliente(1L)
				.listaPedidoProduto(listaPedidoProduto)
				.build();
		PedidoControllerOutputDTO pedidoControllerOutputDTO = pedidoService.gerenciaPedido(pedidoControllerInputDTO);
		return pedidoControllerOutputDTO.getCodPedido();
	}

	private void deveTerRetornadoDadosDoPedido(PedidoControllerOutputDTO outputDTO) {
		// Retorno do serviço com sucesso
		assertThat(outputDTO).isNotNull();
		assertThat(outputDTO.getCodPedido()).isNotNull();
		assertThat(outputDTO.getDataPedido()).isNotNull();
		assertThat(outputDTO.getStatusPedido()).isNotNull();
		assertThat(outputDTO.getValorPedido()).isNotNull();
		assertThat(outputDTO.getListaProdutos()).isNotNull();

	}
	
	private void entaoDeveRetornarErroPedidoNaoEncontrado(ResultActions resultado,
			PedidoMensagemException pedidoNaoEncontrado) throws Exception {
		resultado.andExpect(jsonPath("$.codErro",is(pedidoNaoEncontrado.getErrorCode())));
		
	}
	
	private void entaoDeveRetornarErroPedidoDuplicado(ResultActions resultado,
			PedidoMensagemException pedidoDuplicado) throws Exception {
		resultado.andExpect(jsonPath("$.codErro",is(pedidoDuplicado.getErrorCode())));
		
	}
	
}
