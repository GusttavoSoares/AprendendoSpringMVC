package br.com.ifsp.regescweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("*************");
        return "hello"; // Spring renderiza o arquivo que está em templates/hello.html
    }
}
