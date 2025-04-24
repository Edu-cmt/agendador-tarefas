package com.javanauta.agendadortarefas.business.mapper;

import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.entity.Tarefas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel= "spring")
public interface TarefasConverter {

    @Mapping(source = "id", target = "id")
    Tarefas paraTarefaEntity(TarefasDTO tarefasDTO);

    TarefasDTO paraTarefaDTO(Tarefas tarefas);

    List<Tarefas> paraListaDeTarefasEntity(List<TarefasDTO> tarefasDTO);

    List<TarefasDTO> paraListaDeTarefasDTO(List<Tarefas> tarefas);
}
