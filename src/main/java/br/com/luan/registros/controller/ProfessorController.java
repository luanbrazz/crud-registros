package br.com.luan.registros.controller;

import br.com.luan.registros.dto.RequisicaoNovoProfessor;
import br.com.luan.registros.models.Professor;
import br.com.luan.registros.models.StatusProfessor;
import br.com.luan.registros.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/professores")
    public ModelAndView index(){
        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/professores/new")
    public ModelAndView nnew(RequisicaoNovoProfessor requisicao){
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("/professores")  // criação do obj
    public ModelAndView create(@Valid RequisicaoNovoProfessor requisicao, BindingResult bindingResult){

        System.out.println(requisicao);

        if (bindingResult.hasErrors()){
            System.out.println("\n*********TEM ERROS**************\n");

            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor); // comando que insere o obj no bd

            return new ModelAndView("redirect:/professores/" + professor.getId());
        }

    }

    @GetMapping("/professores/{id}")
    public ModelAndView show(@PathVariable Long id){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();

            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);

            return mv;
        }
        // não achou um registro na tabela Professor com o id informado
        else {
            System.out.println("****************** NÃO ACHOU O PROFESSOR DE ID " + id + "*******************");
            return new ModelAndView("redirect:/professores");
        }


    }
}
