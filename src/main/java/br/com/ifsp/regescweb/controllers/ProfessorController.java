package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfessorController {

    @GetMapping("/professores")
    public ModelAndView index(){
        Professor ursula = new Professor("Ursula Callistis", new BigDecimal(10000.0), StatusProfessor.APOSENTADO);
        ursula.setiD(1L);
        Professor lotte = new Professor("Lotte Jansson", new BigDecimal(5000.0), StatusProfessor.ATIVO);
        lotte.setiD(2L);
        Professor sucy = new Professor("Sucy Manbavaran", new BigDecimal(15000.0), StatusProfessor.INATIVO);
        sucy.setiD(3L);
        List<Professor> professores = Arrays.asList(ursula, lotte, sucy);

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }
}
