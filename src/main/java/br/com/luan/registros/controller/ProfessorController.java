package br.com.luan.registros.controller;

import br.com.luan.registros.models.Professor;
import br.com.luan.registros.models.StatusProfessor;
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
        Professor batman = new Professor("Batman", new BigDecimal(5000.0), StatusProfessor.ATIVO);
        batman.setId(1l);
        Professor coringa = new Professor("Coringa", new BigDecimal(10000.0), StatusProfessor.INATIVO);
        coringa.setId(2l);
        List<Professor> professores = Arrays.asList(batman,coringa);

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }
}
