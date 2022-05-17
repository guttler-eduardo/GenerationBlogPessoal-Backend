package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {

        usuarioRepository
                .save(new Usuario (0L, "nomeTeste", "teste@email.com", "senhateste", "https://i.imgur.com/FETvs2O.jpg"));
        usuarioRepository
                .save(new Usuario (0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg"));
        usuarioRepository
                .save(new Usuario (0L, "nomeTeste3", "teste3@email.com", "senhateste3", "https://i.imgur.com/FETvs2O.jpg"));
        usuarioRepository
                .save(new Usuario (0L, "nomeTeste4", "teste4@email.com", "senhateste4", "https://i.imgur.com/FETvs2O.jpg"));
    }

    @Test
    @DisplayName("Retornar um usuário")
    public void retornarUsuario() {
        Optional<Usuario> user = usuarioRepository.findByUsuario("teste5@gmail.com");
        assertEquals("teste5@gmail.com", user.get().getUsuario());
    }


    @Test
    @DisplayName("Retornar três usuários")
    public void retornarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("nomeTeste5");
        assertEquals(3, usuarios.size());
        assertEquals("nomeTeste6", usuarios.get(0).getNome());
        assertEquals("nomeTeste7", usuarios.get(1).getNome());
        assertEquals("nomeTeste8", usuarios.get(2).getNome());
    }
}