package com.javanauta.agendadortarefas.business;

import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.business.mapper.TarefasConverter;
import com.javanauta.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.javanauta.agendadortarefas.infrastructure.entity.Tarefas;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusTarefa;
import com.javanauta.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final TarefasUpdateConverter tarefasUpdateConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO salvaTarefa(String token, TarefasDTO tarefasDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));

        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusTarefa(StatusTarefa.PENDENTE);
        tarefasDTO.setEmailUsuario(email);
        Tarefas tarefas = tarefaConverter.paraTarefaEntity(tarefasDTO);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefas));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListaDeTarefasDTO(tarefasRepository
                .findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmailUsuario(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        List<Tarefas> tarefasEntity = tarefasRepository.findByEmailUsuario(email);
        return tarefaConverter.paraListaDeTarefasDTO(tarefasEntity);
    }

    public void deletaTarefaPorID(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa, Id não encontrado", e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusTarefa statusTarefa, String id) {
        try {
            Tarefas tarefaEntity = tarefasRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Id não encontrado"));
            tarefaEntity.setStatusTarefa(statusTarefa);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefaEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da Tarefa", e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO tarefasDTO, String id){
        Tarefas tarefaEntity = tarefasRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Id não encontrado"));
        tarefasUpdateConverter.updateTarefas(tarefasDTO, tarefaEntity);
        tarefaEntity.setDataAlteracao(LocalDateTime.now());
        /*MapperStruct permite que usemos o objeto tarefaEntity depois de atualizado, então só precisamos salvar
        * as alterações no banco de dados e retornar a conversao da entity para DTO*/
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefaEntity));

    }
}
