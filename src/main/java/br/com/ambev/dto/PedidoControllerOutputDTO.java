package br.com.ambev.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.ambev.enumerator.StatusPedido;
import br.com.ambev.enumerator.StatusProcessamento;
import br.com.ambev.util.OutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de saida com informações de pedido dos serviços de gerenciamento e consulta 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PedidoControllerOutputDTO implements OutputDTO<PedidoControllerOutputDTO>{
	
	//Retorno com informações do pedido
	private static final long serialVersionUID = 1L;

	private Long codPedido;
	
	private BigDecimal valorPedido;
	
	private LocalDate dataPedido;

	private StatusPedido statusPedido;

	private Set<PedidoProdutoOutputDTO> listaProdutos;	

	// Retorno mensagem de status do serviço
	private StatusProcessamento codStatus;
	private String codRetorno;
	private String dscMensagem;

}
