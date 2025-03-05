package org.example.cabinet.controller;

import jakarta.annotation.Resource;
import org.example.cabinet.service.BoxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoxController {

    @Value("${cabinet.filepath}")
    private String filepath;

    @Resource
    private BoxService boxService;

    @PostMapping
    public void test() {

    }
}
