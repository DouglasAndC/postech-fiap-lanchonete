package br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto

data class CashOut(val amount: Int) {
    init {
        require(amount >= 0) { "O valor do saque deve ser não negativo." }
    }
}
