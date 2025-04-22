package com.javanauta.agendadortarefas.infrastructure.repository;

import com.javanauta.agendadortarefas.infrastructure.entity.Tarefas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<Tarefas, String> {

    List<Tarefas> findByDataEventoBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Tarefas> findByEmailUsuario(String email);
}
