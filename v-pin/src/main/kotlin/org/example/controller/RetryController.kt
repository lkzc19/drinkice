package org.example.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RetryController {

    private val logger = KotlinLogging.logger {}

    private val retryMap = mutableMapOf<String, Int>()

    @RequestMapping("/retry")
    fun retry(retryId: String, retryCount: Int): ResponseEntity<String> {
        retryMap[retryId]?.let {
            val count = it - 1
            if (count == 0) {
                retryMap.remove(retryId)
                return ResponseEntity.ok().body(retryId)
            }
            retryMap[retryId] = count
        } ?: run {
            retryMap[retryId] = retryCount
        }
        logger.info { "retry: [$retryId = ${retryMap[retryId]}]" }
        return ResponseEntity.badRequest().body(retryId)
    }
}