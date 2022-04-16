package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.dto.RequisicaoFormProfessor;
import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import br.com.ifsp.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("")
    public ModelAndView index() {

        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicao) {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());

        return mv;
    }



    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("\n*********** TEM ERROS **************\n");
            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            // Para cada um dos atributos do formulário o spring coloca os valores nesse objeto (nome dos atributos devem ser iguais ao nome dos campos)
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor);
            return new ModelAndView("redirect:/professores/" + professor.getiD());
        }

    }
    @GetMapping("/{iD}")
    public ModelAndView show(@PathVariable Long iD){
        Optional <Professor> optional = this.professorRepository.findById(iD);

        if (optional.isPresent()){
            Professor professor = optional.get();

            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);
            return mv;
        }
        //Não achou um registro na tabela Professor com o id informado
        else{
            System.out.println("$$$$$$$$$$$$$$$$$ nao achou o professor de iD " +iD + "$$$$$$$$");
            return new ModelAndView("redirect:/professores");
        }

    }
    @GetMapping ("/{iD}/edit")
    public ModelAndView edit(@PathVariable Long iD, RequisicaoFormProfessor requisicao){
        Optional <Professor> optional = this.professorRepository.findById(iD);


        if (optional.isPresent()){
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professoriD", professor.getiD());
            mv.addObject("listaStatusProfessor", StatusProfessor.values());

            return mv;
        }
        else{
            System.out.println("$$$$$$$$$$$$$$$$$ nao achou o professor de iD " +iD + "$$$$$$$$");
            return new ModelAndView("redirect:/professores");
        }
    }

}
