package com.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quiz.model.Assunto;

@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {
    @Query("SELECT a FROM Assunto a " +
           "WHERE a.nivel.nivelId = :nivelId")
    List<Assunto> findByNivelNivelId(@Param("nivelId") Integer nivelId);
}
