package org.example.cabinet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController {

    @RequestMapping("/ping")
    public String ping() {
        log.info("ping");
        return "pong...";
    }
}
