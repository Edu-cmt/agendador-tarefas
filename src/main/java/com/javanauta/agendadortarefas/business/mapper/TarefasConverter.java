package com.javanauta.agendadortarefas.business.mapper;

import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.entity.Tarefas;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface TarefasConverter {

    Tarefas paraTarefaEntity(TarefasDTO tarefasDTO);

    TarefasDTO paraTarefaDTO(Tarefas tarefas);
}
