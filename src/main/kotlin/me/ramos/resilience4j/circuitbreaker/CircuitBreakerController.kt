package me.ramos.resilience4j.circuitbreaker

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CircuitBreakerController(
    private val circuitBreakerService: CircuitBreakerService
) {

    @GetMapping("/api-call")
    fun apiCall(@RequestParam param: String): String {
        return circuitBreakerService.process(param)
    }
}