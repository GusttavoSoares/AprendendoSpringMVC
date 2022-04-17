package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.dto.RequisicaoFormProfessor;
import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import br.com.ifsp.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView show(@PathVariable Long iD) {
        Optional<Professor> optional = this.professorRepository.findById(iD);

        if (optional.isPresent()) {
            Professor professor = optional.get();

            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);
            return mv;
        }
        //Não achou um registro na tabela Professor com o id informado
        else {
            System.out.println("$$$$$$$$$$$$$$$$$ nao achou o professor de iD " + iD + "$$$$$$$$");
            return this.retornaErroProfessor("SHOW ERROR: Professor #" + iD + " não encontrado!");
        }
    }

    @GetMapping("/{iD}/edit")
    public ModelAndView edit(@PathVariable Long iD, RequisicaoFormProfessor requisicao) {
        Optional<Professor> optional = this.professorRepository.findById(iD);


        if (optional.isPresent()) {
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professoriD", professor.getiD());
            mv.addObject("listaStatusProfessor", StatusProfessor.values());

            return mv;
        } else {
            System.out.println("$$$$$$$$$$$$$$$$$ nao achou o professor de iD " + iD + "$$$$$$$$");
            return this.retornaErroProfessor("EDIT ERROR: Professor #" + iD + " não encontrado!");
        }
    }

    @PostMapping("/{iD}")
    public ModelAndView update(@PathVariable Long iD, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            mv.addObject("professoriD", iD);
            return mv;

        } else {
            Optional<Professor> optional = this.professorRepository.findById(iD);

            if (optional.isPresent()) {
                Professor professor = requisicao.toProfessor(optional.get());
                this.professorRepository.save(professor);

                return new ModelAndView("redirect:/professores/" + professor.getiD());

            }
            //Não achou um registro na tabela Professor com o id informado
            else {
                return this.retornaErroProfessor("UPDATE ERROR: Professor #" + iD + " não encontrado!");

            }
        }
    }

    @GetMapping("/{iD}/delete")
    public ModelAndView delete(@PathVariable Long iD) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        try {
            this.professorRepository.deleteById(iD);
            mv.addObject("mensagem", "Professor #" + iD + " foi deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroProfessor("DELETE ERROR: Professor #" + iD + " não encontrado!");
        }
        return mv;
    }

    private ModelAndView retornaErroProfessor(String msg) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }
}
