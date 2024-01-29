package br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GenerateQrCodeResponse(
    @JsonProperty(value = "in_store_order_id")
    val inStoreOrderId: String,
    @JsonProperty(value = "qr_data")
    val qrData: String
)
