package org.liuchuanhung.aop.logging.controller;

import org.liuchuanhung.aop.logging.annotation.Loggable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    @Loggable
    @GetMapping("/{dummyPath}")
    public String greeting(@RequestParam("name") String name , @PathVariable("dummyPath") String dummyPath) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ");
        sb.append(name);
        sb.append("!");
        return sb.toString();
    }

    @Loggable
    @PostMapping
    public String greeting2(@RequestBody String request) {
        return "nice to meet you.";
    }
}