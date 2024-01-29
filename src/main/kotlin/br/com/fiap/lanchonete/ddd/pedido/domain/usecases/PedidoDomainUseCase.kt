package br.com.fiap.lanchonete.ddd.pedido.domain.usecases

import br.com.fiap.lanchonete.ddd.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.exception.PedidoExceptionEnum
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.GetOrderResponse
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PedidoDomainUseCase(private val pedidoRepositoryGateway: PedidoRepositoryGateway) {


    fun findPedidoById(id: Long) =
        pedidoRepositoryGateway.findPedidoById(id)
        ?: throw BusinessException(PedidoExceptionEnum.PEDIDO_NOT_FOUND)

    @Transactional
    fun create(pedido: Pedido) = pedidoRepositoryGateway.save(pedido)
    fun getAll(pageable: Pageable) = pedidoRepositoryGateway.findAll(pageable)
    @Transactional
    fun checkout(id: Long) =
        findPedidoById(id).also {
            if(it.status == StatusPedido.RECEBIDO){
                it.status = StatusPedido.EM_PREPARACAO
                pedidoRepositoryGateway.save(it)
            }else{
                throw BusinessException(PedidoExceptionEnum.PEDIDO_STATUS_INVALID)
            }
        }
    @Transactional
    fun updatePedido(pedido: Pedido):Pedido{
        return pedidoRepositoryGateway.save(pedido)
    }
    fun updateStatus(pedido: Pedido, statusPedido: StatusPedido): Pedido{
        pedido.status = statusPedido

        return updatePedido(pedido)
    }

    fun validatePedidoPagamento(orderResponse: GetOrderResponse): Boolean{
        return orderResponse.status == GetOrderResponse.STATUS_CLOSED &&
                orderResponse.orderStatus == GetOrderResponse.PAID_STATUS
    }

    @Transactional
    fun closePedidoPagamento(pedido: Pedido, orderResponse: GetOrderResponse?){
        if(orderResponse?.let { validatePedidoPagamento(it) } == true){
            pedido.pagamento = StatusPagamento.APROVADO
        } else {
            pedido.pagamento = StatusPagamento.RECUSADO
        }
        updatePedido(pedido)
    }

}