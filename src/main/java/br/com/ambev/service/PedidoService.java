package br.com.ambev.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ambev.dto.PedidoControllerInputDTO;
import br.com.ambev.dto.PedidoControllerOutputDTO;
import br.com.ambev.dto.PedidoProdutoOutputDTO;
import br.com.ambev.entity.Pedido;
import br.com.ambev.entity.PedidoProduto;
import br.com.ambev.entity.PedidoProduto.PK;
import br.com.ambev.enumerator.StatusPedido;
import br.com.ambev.exception.BusinessException;
import br.com.ambev.exception.PedidoMensagemException;
import br.com.ambev.repository.PedidoRepository;
import br.com.ambev.util.GeradorCodigoUtil;
import jakarta.validation.constraints.NotNull;

@Service
public class PedidoService {

	private PedidoRepository pedidoRepository;
	private GeradorCodigoUtil geradorCodigoUtil;

	/*
	 * Gerencia os dados de soma do valor total do pedido e controle de duplicidade
	 */
	public PedidoControllerOutputDTO gerenciaPedido(PedidoControllerInputDTO pedidoControllerInputDTO)
			throws BusinessException {
		Pedido pedido = geraPedidoSolicitado(pedidoControllerInputDTO);
		verificaDuplicidadePedido(pedido,pedidoControllerInputDTO.getCodCliente());		
		calculaEGravaPedido(pedido);
		return criaOutputDTO(pedido);
	}

	/* Cria um objeto pedido com os dados da solicitação feita */
	private Pedido geraPedidoSolicitado(PedidoControllerInputDTO pedidoControllerInputDTO) {		
		return Pedido.builder()
				.dataPedido(LocalDate.now())
				.statusPedido(StatusPedido.CRIADO)
				.pedidoProduto(pedidoControllerInputDTO.getListaPedidoProduto().stream()
						.map(produto ->PedidoProduto.builder()
								.nomeProduto(produto.getNomeProduto())
								.pk(PK.builder().codPedido(geradorCodigoUtil.geradorPedido()).codProduto(produto.getCodProduto()).build())
								.quantidadeProduto(produto.getQuantidadeProduto())
								.valorLote(produto.getValorUnidade()*produto.getQuantidadeProduto().intValue())
								.valorUnidade(produto.getValorUnidade())
								.build())
						.collect(Collectors.toSet()))
				.build();
		
	}

	/* Verifica se já existe um pedido feito */
	public void verificaDuplicidadePedido(Pedido pedido, Long codCliente)
			throws BusinessException {
		Optional<Pedido> listaPedidosAntigos = pedidoRepository.findByCodClienteAndStatusPedidoOrderByDataPedidoDesc(codCliente,StatusPedido.CRIADO);
		/*Valida pedidos, primeiro por quantidade de produtos e depois por quantidade de lotes de produtos*/
		if(!listaPedidosAntigos.isEmpty()) {
		
			Set <PedidoProduto> pedidoAntigo = listaPedidosAntigos.get().getPedidoProduto();
			Set <PedidoProduto> pedidoNovo = pedido.getPedidoProduto();
			AtomicInteger produtosCompativeis = new AtomicInteger();
			if(pedidoAntigo.size()==pedidoNovo.size()) {
				
				pedidoNovo.forEach(produtoNovo ->{
					pedidoAntigo.forEach(produtoAntigo ->{
						//Valida código do produto e quantidade dos pedidos novos e antigos
						if(produtoNovo.getPk().getCodProduto().equals(produtoAntigo.getPk().getCodProduto())&&
								produtoNovo.getQuantidadeProduto().equals(produtoAntigo.getQuantidadeProduto())) {
							produtosCompativeis.getAndIncrement();
						}
					});
				});
				//Verifica se o número de produtos compativeis é igual ao número de produtos do pedido, se for joga exceção
				if(produtosCompativeis.intValue() > 0 && pedidoAntigo.size()==produtosCompativeis.intValue()){
					throw new BusinessException(PedidoMensagemException.PEDIDO_DUPLICADO,pedido.getCodPedido());
				}
			}			
		}	

	}

	/* Calcula o valor total de todos os produtos e salva o pedido */
	public void calculaEGravaPedido(Pedido pedido) {
		Double valorTotal = pedido.getPedidoProduto().stream()
				.mapToDouble(pedidoProduto -> pedidoProduto.getValorLote()).sum();
		pedido.setValorTotalPedido(valorTotal);
		pedidoRepository.save(pedido);

	}

	/* Consulta o pedido pelo seu código e retorna todas as informações */
	public PedidoControllerOutputDTO consultaPedido(@NotNull Long codPedido) throws BusinessException {
		Pedido pedido = pedidoRepository.findById(codPedido)
				.orElseThrow(() -> new BusinessException(PedidoMensagemException.PEDIDO_NAO_ENCONTRADO, codPedido));
		return criaOutputDTO(pedido);
	}

	/* Cria objeto de saida dos serviços */
	public PedidoControllerOutputDTO criaOutputDTO(Pedido pedido) {
		return PedidoControllerOutputDTO.builder().codPedido(pedido.getCodPedido()).dataPedido(pedido.getDataPedido())
				.statusPedido(pedido.getStatusPedido()).valorPedido(pedido.getValorTotalPedido())
				.listaProdutos(pedido.getPedidoProduto().stream().map(pedidoProduto -> PedidoProdutoOutputDTO.builder()
						.codProduto(pedidoProduto.getPk().getCodProduto()).nomeProduto(pedidoProduto.getNomeProduto())
						.quantidadeProduto(pedidoProduto.getQuantidadeProduto())
						.valorUnidade(pedidoProduto.getValorUnidade()).build()).collect(Collectors.toSet()))
				.build();

	}

}
