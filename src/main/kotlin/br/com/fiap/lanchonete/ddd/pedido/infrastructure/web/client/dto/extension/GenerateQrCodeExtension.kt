package br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.extension

import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.GenerateQrCodeRequest
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.Item

fun Pedido.toGenerateQrCodeRequest(): GenerateQrCodeRequest {
    return GenerateQrCodeRequest(
        description = "Pedido do Cliente -${this.cliente?.nome ?: "N/I"}",
        externalReference = "reference_${this.id ?: 0}",
        items = this.produtos.map { it.toMercadoPagoItemDTO() },
        notificationUrl = "https://www.yourserver.com/notifications",
        title = "Product order",
        totalAmount = this.precoTotal
    )
}

fun Combo.toMercadoPagoItemDTO(): Item {
    return Item(
        skuNumber = "sku_number_${this.id ?: 0}",
        category = this.produto.categoria.name,
        title = this.produto.nome,
        description = this.produto.descricao,
        unitPrice = this.produto.preco,
        quantity = this.quantidade,
        unitMeasure = "unit",
        totalAmount = this.calcularPrecoTotal()
    )
}