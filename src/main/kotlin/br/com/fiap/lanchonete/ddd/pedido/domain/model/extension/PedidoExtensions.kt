package br.com.fiap.lanchonete.ddd.pedido.domain.model.extension

import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequestDto
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponseDto
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido

fun Pedido.toDTO() = PedidoResponseDto(uuid = uuid)
fun PedidoRequestDto.toEntity() = Pedido(uuid = uuid)
