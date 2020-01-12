package com.halodoc.ratelimit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/Foo")
public class Foo {

    @GetMapping(value = "/a")
    public void a()
    {
        System.out.println("Hi this is FOO a");

    }

    @GetMapping("/b")
    public void b()
    {
        System.out.println("Hi this is FOO b");

    }
}
