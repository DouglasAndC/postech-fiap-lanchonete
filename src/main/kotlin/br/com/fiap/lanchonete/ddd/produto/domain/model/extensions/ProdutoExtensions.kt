package br.com.fiap.lanchonete.ddd.produto.domain.model.extensions

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.application.dto.response.ProdutoResponse
import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto

fun Produto.toDTO(): ProdutoResponse = ProdutoResponse(id = id,nome = nome, categoria = categoria, descricao = descricao, imagens = imagens)

fun ProdutoRequest.toEntity(): Produto = Produto(nome = nome, categoria = categoria, descricao = descricao, imagens = imagens)