package br.com.fiap.lanchonete.ddd.pedido.application.dto.response

import br.com.fiap.lanchonete.ddd.produto.application.dto.response.ProdutoResponse

data class ComboResponse(val id: Long?, val quantidade: Int?, val produto: ProdutoResponse?)
