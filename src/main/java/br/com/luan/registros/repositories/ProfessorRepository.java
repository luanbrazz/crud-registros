package br.com.luan.registros.repositories;

import br.com.luan.registros.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessorRepository extends JpaRepository<Professor, Long> {

}
