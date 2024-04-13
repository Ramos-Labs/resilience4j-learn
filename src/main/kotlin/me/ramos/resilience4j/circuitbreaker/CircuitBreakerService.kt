package me.ramos.resilience4j.circuitbreaker

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import me.ramos.resilience4j.exception.IgnoreException
import me.ramos.resilience4j.exception.RecordException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @CircuitBreaker(name = SIMPLE_CIRCUIT_BREAKER_CONFIG, fallbackMethod = "fallback")
    fun process(param: String): String {
        return callAnotherServer(param)
    }

    private fun fallback(param: String, ex: Exception): String {
        log.info("fallback! your request is $param")
        return "Recovered $ex"
    }

    private fun callAnotherServer(param: String): String {
        when (param) {
            "a" -> throw RecordException("record exception")
            "b" -> throw IgnoreException("ignore exception")
            "c" -> Thread.sleep(4000)
        }

        return param
    }

    companion object {
        private const val SIMPLE_CIRCUIT_BREAKER_CONFIG = "simpleCircuitBreakerConfig"
    }
}