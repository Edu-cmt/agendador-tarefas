package com.javanauta.agendadortarefas.controller;


import com.javanauta.agendadortarefas.business.TarefasService;
import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusTarefa;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> salvaTarefa(@RequestBody TarefasDTO tarefasDTO,
                                                  @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.salvaTarefa(token, tarefasDTO));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorPeriodo(
                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmailDeUsuario(@RequestHeader("Authorization") String token){
        return  ResponseEntity.ok(tarefasService.buscaTarefasPorEmailUsuario(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaTarefa(@PathVariable String id){
        tarefasService.deletaTarefaPorID(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusDaTarefa(@RequestParam("status")StatusTarefa statusTarefa,
                                                           @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.alteraStatus(statusTarefa, id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> atualizaTarefas(@RequestBody TarefasDTO tarefasDTO,
                                                      @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.updateTarefas(tarefasDTO, id));
    }

}
