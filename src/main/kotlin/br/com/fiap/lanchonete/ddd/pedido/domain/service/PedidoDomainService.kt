package br.com.fiap.lanchonete.ddd.pedido.domain.service

import br.com.fiap.lanchonete.ddd.pedido.domain.repository.PedidoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PedidoDomainService(private val pedidoRepository: PedidoRepository) {

}