package br.com.ifsp.regescweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {


    @GetMapping("/hello")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("hello"); // nome do arquivo html a ser renderizado
        mv.addObject("nome", "José!");
        return mv; // Spring renderiza o arquivo que está em templates/hello.html
    }

    @GetMapping("/hello-model")
    public String hello(Model model) {
        model.addAttribute("nome", "Zezinho");
        return "hello";  // Spring renderiza o arquivo que está em templates/hello.html
    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request) {
        request.setAttribute("nome", "Samuka");
        return "hello";  // Spring renderiza o arquivo que está em templates/hello.html
        // Lembrando que esse hello.html renderizado está somente no lado servidor
    }

}
