package br.com.luan.registros.controller;

import br.com.luan.registros.dto.RequisicaoFormProfessor;
import br.com.luan.registros.models.Professor;
import br.com.luan.registros.models.StatusProfessor;
import br.com.luan.registros.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/professores")
@Controller
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("")
    public ModelAndView index(){
        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicao){
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("")  // criação do obj
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){

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

    @GetMapping("/{id}")
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

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);

            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", professor.getId());
            mv.addObject("listaStatusProfessor", StatusProfessor.values());

            return mv;
        }

        // não achou um registro na tabela Professor com o id informado
        else {
            System.out.println("****************** NÃO ACHOU O PROFESSOR DE ID " + id + "*******************");
            return new ModelAndView("redirect:/professores");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }
        else {
            Optional<Professor> optional = this.professorRepository.findById(id);

            if (optional.isPresent()) {
                Professor professor = requisicao.toProfessor(optional.get());
                this.professorRepository.save(professor);

                return new ModelAndView("redirect:/professores/" + professor.getId());
            }
            // não achou um registro na tabela Professor com o id informado
            else {
                System.out.println("############ NÃO ACHOU O PROFESSOR DE ID "+ id + " ############");
                return this.retornaErroProfessor("UPDATE ERROR: Professor #" + id + " não encontrado no banco!");
            }
        }
    }
}
