package br.com.ifsp.regescweb.repositories;


import br.com.ifsp.regescweb.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> { // Long é o tipo da chave primária da entidade

}
