package org.example.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @RequestMapping("/ping")
    fun ping(): String = "pong..."
}