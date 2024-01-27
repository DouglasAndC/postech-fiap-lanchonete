package br.com.fiap.lanchonete.ddd.pedido.domain.usecases

import br.com.fiap.lanchonete.ddd.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.exception.PedidoExceptionEnum
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

}