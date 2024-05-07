package br.com.authservice.infrastructure.controllers;

import br.com.authservice.application.RegistrarUsuarioService;
import br.com.authservice.infrastructure.controllers.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioService registrarUsuarioService;

    public UsuarioController(RegistrarUsuarioService registrarUsuarioService) {
        this.registrarUsuarioService = registrarUsuarioService;
    }

    @GetMapping
    public ResponseEntity<String> entreiAqui() {
        return ResponseEntity.ok("Entrei aqui");
    }

    @PostMapping
    public ResponseEntity<Long> registrarUsuario(@Validated @RequestBody UsuarioDTO usuarioDTO) {
        Long usuarioId = registrarUsuarioService.registrarUsuario(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getSenha(),
                usuarioDTO.getPermissoes()
        );
        return ResponseEntity.ok(usuarioId);
    }
}