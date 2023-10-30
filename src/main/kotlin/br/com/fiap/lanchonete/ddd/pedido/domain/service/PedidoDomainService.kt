package br.com.fiap.lanchonete.ddd.pedido.domain.service

import br.com.fiap.lanchonete.ddd.pedido.domain.exception.PedidoExceptionEnum
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.repository.PedidoRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PedidoDomainService(private val pedidoRepository: PedidoRepository) {
    @Transactional
    fun create(pedido: Pedido) = pedidoRepository.save(pedido)
    fun getAll(pageable: Pageable) = pedidoRepository.findAll(pageable)
    fun checkout(id: Long) =
        pedidoRepository.findPedidoById(id)?.also {
            if(it.status == StatusPedido.RECEBIDO){
                it.status = StatusPedido.EM_PREPARACAO
                pedidoRepository.save(it)
            }else{
                throw BusinessException(PedidoExceptionEnum.PEDIDO_STATUS_INVALID)
            }
        }  ?: throw BusinessException(PedidoExceptionEnum.PEDIDO_NOT_FOUND)
}