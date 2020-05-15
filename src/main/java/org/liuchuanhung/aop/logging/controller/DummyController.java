package org.liuchuanhung.aop.logging.controller;

import org.liuchuanhung.aop.logging.annotation.Loggable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    @Loggable
    @GetMapping
    public String greeting(@RequestParam("name") String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ");
        sb.append(name);
        sb.append("!");
        return sb.toString();
    }
}