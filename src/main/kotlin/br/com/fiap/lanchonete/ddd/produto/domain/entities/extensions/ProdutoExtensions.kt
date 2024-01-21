package br.com.fiap.lanchonete.ddd.produto.domain.entities.extensions

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.application.dto.response.ProdutoResponse
import br.com.fiap.lanchonete.ddd.produto.domain.entities.Produto

fun Produto.toDTO(): ProdutoResponse = ProdutoResponse(id = id!!,nome = nome, categoria = categoria, descricao = descricao,preco = preco, imagens = imagens)

fun ProdutoRequest.toEntity(): Produto = Produto(id = null, nome = nome, categoria = categoria, descricao = descricao, preco = preco)