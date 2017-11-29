package com.thirdstart.spring.zerotohero.controllers

import com.thirdstart.spring.zerotohero.domain.Greeting
import org.springframework.web.bind.annotation.GetMapping

import java.util.concurrent.atomic.AtomicLong

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloWorldController {

    static final String template = "Hello, %s!"
    final AtomicLong counter = new AtomicLong()

    @GetMapping('/hello-world')
    @ResponseBody Greeting sayHello(
            @RequestParam(value="name", required=false, defaultValue="Stranger") String name
    ) {
        return new Greeting(
            id: counter.incrementAndGet() * -1,
            content: String.format(template, name)
        )
    }

}