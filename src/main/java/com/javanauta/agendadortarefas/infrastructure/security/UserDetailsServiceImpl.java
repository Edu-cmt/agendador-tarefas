package com.javanauta.agendadortarefas.infrastructure.security;


import com.javanauta.agendadortarefas.business.dto.UsuarioDTO;
import com.javanauta.agendadortarefas.infrastructure.security.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsuarioClient usuarioClient;

    /* Implementação do método para carregar detalhes do usuário pelo e-mail */
    public UserDetails carregaDadosUsuario (String email, String token){

        UsuarioDTO usuarioDTO = usuarioClient.buscaUsuario(email, token);
        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
