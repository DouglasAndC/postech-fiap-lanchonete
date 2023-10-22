package br.com.fiap.lanchonete.exception

import br.com.fiap.lanchonete.exception.enums.ExceptionEnum

class BusinessException(val exceptionEnum: ExceptionEnum) : Exception()