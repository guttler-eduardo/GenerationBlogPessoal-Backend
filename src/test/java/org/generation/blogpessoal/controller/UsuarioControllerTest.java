package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import java.net.http.HttpHeaders;
import java.util.Optional;
import org.generation.blogpessoal.model.Usuario;
//import org.generation.blogpessoal.model.UsuarioLogin;
//import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    //@Autowired
    //private UsuarioRepository usuarioRepository;


    @Test
    @Order(1)
    @DisplayName("Registrar novo usuário")
    public void registrarNovoUsuario() {

        HttpEntity<Usuario> request = new HttpEntity<Usuario>(new Usuario
        		(0L, "nomeTeste", "teste@email.com", "senhateste", "https://i.imgur.com/FETvs2O.jpg"));
        ResponseEntity<Usuario> response = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request.getBody().getNome(), response.getBody().getNome());
        assertEquals(response.getBody().getUsuario(), response.getBody().getUsuario());
    }
    
    /*
    @Test
    @Order(2)
    @DisplayName("Logar Usuário")
    public void logarUsuario() {

        Usuario user = new Usuario (0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg");
        Optional<UsuarioLogin> userLogin = usuarioService.autenticaUsuario(
                Optional.of(new UsuarioLogin (user.getUsuario(), user.getSenha())));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", userLogin.get().getToken());

        HttpEntity<Optional<UsuarioLogin>> request = new HttpEntity <Optional<UsuarioLogin>> (
                Optional.of(new UsuarioLogin (user.getUsuario(), user.getSenha())), httpHeaders);

        ResponseEntity<Usuario> response = testRestTemplate
                .exchange("/usuarios/logar", HttpMethod.POST, request, Usuario.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    */

    @Test
    @Order(3)
    @DisplayName("Usuário repetido")
    public void usuarioRepetido() {

        usuarioService.cadastraUsuario(new Usuario
        		(0L, "nomeTeste3", "teste3@email.com", "senhateste3", "https://i.imgur.com/FETvs2O.jpg"));
        HttpEntity<Usuario> request = new HttpEntity<Usuario>(new Usuario (0L, "Gaia Lois", "gaia1", "gaia@email.com", "gaia1234"));
        ResponseEntity<Usuario> response = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Atualizar usuário")
    public void atualizarUsuario() {

        Optional<Usuario> user = usuarioService.cadastraUsuario(new Usuario
        		(0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg"));
        Usuario updatedUser = new Usuario(user.get().getId(), "Aloes Aragon", "aragon1", "aloes@email.com", "aloes1234");
        HttpEntity<Usuario> request = new HttpEntity<Usuario>(updatedUser);
        ResponseEntity<Usuario> response = testRestTemplate.withBasicAuth("root", "root")
        		.exchange("/usuarios/update", HttpMethod.PUT, request, Usuario.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser.getNome(), response.getBody().getNome());
        assertEquals(updatedUser.getUsuario(), response.getBody().getUsuario());
    }

    @Test
    @Order(5)
    @DisplayName("Ver todos os usuários")
    public void getAllUsers() {

        usuarioService.cadastraUsuario(new Usuario
        		(0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg"));
        usuarioService.cadastraUsuario(new Usuario (0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg"));
        ResponseEntity<String> response = testRestTemplate.withBasicAuth("root", "root")
        		.exchange("/usuarios", HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Ver usuários por ID")
    public void getById() {

        Optional<Usuario> usuario = usuarioService.cadastraUsuario(new Usuario
        		(0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg"));
        ResponseEntity<Usuario> response = testRestTemplate.withBasicAuth("root", "root")
                .exchange("/usuarios/" + usuario.get().getId(), HttpMethod.GET, null, Usuario.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario.get().getUsuario(), response.getBody().getUsuario());
    }

    @Test
    @Order(7)
    @DisplayName("Remover usuário")
    public void removerUsuario() {

        Optional<Usuario> usuario = usuarioService.cadastraUsuario(new Usuario
        		(0L, "nomeTeste2", "teste2@email.com", "senhateste2", "https://i.imgur.com/FETvs2O.jpg"));
        ResponseEntity<String> response = testRestTemplate.withBasicAuth("root", "root")
                .exchange("/usuarios/" + usuario.get().getId(), HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}