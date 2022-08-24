package br.com.luan.registros.dto;

import br.com.luan.registros.models.Professor;
import br.com.luan.registros.models.StatusProfessor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

// (Data Transfer Object) -> proteje contra inserções maliciosas no obj
public class RequisicaoFormProfessor {

    @NotBlank
    @NotNull
    private String nome; // em caso de erro, NotBlank.requisicaoNovoProfessor.nome

    private StatusProfessor statusProfessor;

    @DecimalMin(value = "0.0")
    @NotNull
    private BigDecimal salario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusProfessor getStatusProfessor() {
        return statusProfessor;
    }

    public void setStatusProfessor(StatusProfessor statusProfessor) {
        this.statusProfessor = statusProfessor;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Professor toProfessor(){
        Professor professor = new Professor();
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.statusProfessor);

        return professor;
    }

    public Professor toProfessor(Professor professor){
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.statusProfessor);
        return professor;
    }


    public void fromProfessor(Professor professor){
        this.nome = professor.getNome();
        this.salario = professor.getSalario();
        this.statusProfessor = professor.getStatusProfessor();
    }

    @Override
    public String toString() {
        return "RequisicaoNovoProfessor{" +
                "nome='" + nome + '\'' +
                ", statusProfessor=" + statusProfessor +
                ", salario=" + salario +
                '}';
    }
}
