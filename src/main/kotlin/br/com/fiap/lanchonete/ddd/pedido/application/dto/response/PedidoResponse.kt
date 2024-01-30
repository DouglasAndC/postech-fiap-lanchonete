package br.com.fiap.lanchonete.ddd.pedido.application.dto.response

import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import java.time.LocalDateTime

data class PedidoResponse(
    val id: Long?,
    val status: StatusPedido?,
    val pagamento: StatusPagamento?,
    val cliente: ClienteResponse?,
    val produtos: List<ComboResponse>,
    val qr: String?,
    val createDate: LocalDateTime?,
    val updateDate: LocalDateTime?
)