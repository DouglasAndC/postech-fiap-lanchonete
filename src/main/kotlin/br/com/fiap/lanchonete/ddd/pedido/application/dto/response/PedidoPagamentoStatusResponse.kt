package br.com.fiap.lanchonete.ddd.pedido.application.dto.response

import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPagamento
import java.time.LocalDateTime

data class PedidoPagamentoStatusResponse(
    val id: Long?,
    val pagamento: StatusPagamento?,
    val createDate: LocalDateTime?,
    val updateDate: LocalDateTime?
)