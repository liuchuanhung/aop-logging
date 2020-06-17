package org.liuchuanhung.aop.logging.controller;

import org.liuchuanhung.aop.logging.annotation.Loggable;
import org.springframework.web.bind.annotation.*;

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