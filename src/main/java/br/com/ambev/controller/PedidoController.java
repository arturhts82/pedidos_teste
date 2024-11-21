package br.com.ambev.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ambev.dto.PedidoControllerInputDTO;
import br.com.ambev.dto.PedidoControllerOutputDTO;
import br.com.ambev.exception.BusinessException;
import br.com.ambev.service.PedidoService;
import lombok.NonNull;


@RestController
@RequestMapping(path = PedidoController.path)
public class PedidoController {

	public static final String path = "/api/v1/order";

	@Autowired
	public PedidoService pedidoService;

	/**
	 * Serviço que gerencia e calcula os produtos do pedido
	 * 
	 * @throws BusinessException
	 */
	@PostMapping
	public ResponseEntity<PedidoControllerOutputDTO> gerenciaPedidos(
			@RequestBody @NonNull PedidoControllerInputDTO pedidoControllerInputDTO) throws BusinessException {
		PedidoControllerOutputDTO pedidoControllerOutputDTO = pedidoService.gerenciaPedido(pedidoControllerInputDTO);
		return ResponseEntity.ok(pedidoControllerOutputDTO);
	}

	/**
	 * Serviço que consulta dados do pedido pelo codigo do pedido
	 * 
	 * @throws BusinessException
	 */
	@GetMapping("/{codPedido}")
	public ResponseEntity<PedidoControllerOutputDTO> consultaPedido(@PathVariable @NonNull Long codPedido)
			throws BusinessException {
		PedidoControllerOutputDTO pedidoControllerOutputDTO = pedidoService.consultaPedido(codPedido);
		return ResponseEntity.ok(pedidoControllerOutputDTO);
	}
}
