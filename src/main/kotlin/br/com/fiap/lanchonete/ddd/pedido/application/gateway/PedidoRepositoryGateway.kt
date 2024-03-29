package br.com.fiap.lanchonete.ddd.pedido.application.gateway

import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

interface PedidoRepositoryGateway {
    fun save(pedido: Pedido): Pedido

    @Query("""
            SELECT p FROM Pedido p 
            LEFT JOIN FETCH p.cliente 
            WHERE p.status <> 'FINALIZADO' 
            ORDER BY 
            CASE p.status 
               WHEN 'PRONTO' THEN 1 
               WHEN 'EM_PREPARACAO' THEN 2 
               WHEN 'RECEBIDO' THEN 3 
            END, 
            p.status ASC
            """)
    fun findAll(pageable: Pageable): Page<Pedido>
    fun findPedidoById(id:Long): Pedido?
}