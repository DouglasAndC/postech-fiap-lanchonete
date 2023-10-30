package br.com.fiap.lanchonete.ddd.pedido.application.dto.request

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest

data class PedidoProdutoRequest(val id: Long?, val quantidade: Int, val produto: ProdutoRequest?)
