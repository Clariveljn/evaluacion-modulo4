package wallet.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import wallet.Autenticacion;
import wallet.Usuario;

public class AutenticacionTest {

    @Test
    public void testRegistrarUsuario_EmailValido() {
        Autenticacion autenticacion = new Autenticacion();
        String input = "Clari\nJeldres\n12.345.678-9\nclari@gmail.com\n123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        autenticacion.registrarUsuario(scanner);

        assertTrue(autenticacion.getUsuarios().containsKey("clari@gmail.com"));
    }

    @Test
    public void testRegistrarUsuario_EmailInvalido() {
        Autenticacion autenticacion = new Autenticacion();
        String input = "Maria\nGonzalez\n98.765.432-0\nmariagmail.com\nmaria123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        autenticacion.registrarUsuario(scanner);

        assertFalse(autenticacion.getUsuarios().containsKey("mariagmail.com"));
    }

    @Test
    public void testIniciarSesion_UsuarioExistente() {
        Autenticacion autenticacion = new Autenticacion();
        Usuario usuario = new Usuario("Rosa", "Lara", "12.345.123-9", "rosa@gmail.com", "1234");
        autenticacion.getUsuarios().put("rosa@gmail.com", usuario);

        String input = "rosa@gmail.com\n1234\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Usuario usuarioIniciado = autenticacion.iniciarSesion(scanner);

        assertNotNull(usuarioIniciado);
    }

    @Test
    public void testIniciarSesion_ContraseñaIncorrecta() {
        Autenticacion autenticacion = new Autenticacion();
        Usuario usuario = new Usuario("Juana", "Perez", "12.345.678-9", "juana@gmail.com", "2468");
        autenticacion.getUsuarios().put("juana@gmail.com", usuario);

        String input = "juan@example.com\n8642\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Usuario usuarioIniciado = autenticacion.iniciarSesion(scanner);

        assertNull(usuarioIniciado);
    }
}


