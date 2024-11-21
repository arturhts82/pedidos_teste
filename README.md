Serviço de gerenciamento de pedidos, posssui dois serviços um de gerenciamento de pedidos e outro de consulta de informações de pedidos.
O serviço de gerenciamento é responsável por receber o pedido, calculando seu valor total e verficando inconsistências como duplicidade e após verificado grava as informações atualizadas do pedido.
E o serviço de consulta recebe um número de pedido válido e retorna todas as informações do pedido.
Exemplos de chamadas:

1- Gerenciamento de pedidos:
POST/[http://localhost:8080/api/v1/order]
Body:
{
    "codCliente":"1",
    "listaPedidoProduto":[
        {
            "codProduto":"1",
            "quantidadeProduto":"6",
            "nomeProduto":"CERVEJA",
            "valorUnidade":"10.00"
        }
    ]
}

2- Consulta de pedidos:
GET/http://localhost:8080/api/v1/order/1

