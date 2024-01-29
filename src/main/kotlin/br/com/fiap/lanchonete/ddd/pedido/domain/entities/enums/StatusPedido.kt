package br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums

import br.com.fiap.lanchonete.ddd.pedido.domain.exception.PedidoExceptionEnum
import br.com.fiap.lanchonete.exception.BusinessException

enum class StatusPedido {
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO;

    companion object {
        fun valueOfStatus(customValue: String): StatusPedido {
            return entries.find { it.name.equals(customValue, ignoreCase = true) }
                ?: throw BusinessException(PedidoExceptionEnum.PEDIDO_STATUS_INVALID)
        }
    }
}
