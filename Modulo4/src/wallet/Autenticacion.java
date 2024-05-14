package wallet;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * La clase Autenticacion proporciona métodos para el registro e inicio de sesión de usuarios.
 */
public class Autenticacion {
    private Map<String, Usuario> usuarios;

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Constructor de la clase Autenticacion.
     */
    public Autenticacion() {
        this.setUsuarios(new HashMap<>());
    }

    /**
     * Registra un nuevo usuario.
     * @param scanner, El objeto Scanner para la entrada del usuario.
     */
    public void registrarUsuario(Scanner scanner) {
        System.out.println("Registro de usuario");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Rut: ");
        String rut = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (!validarEmail(email)) {
            System.out.println("El formato del email no es válido. Usuario no creado.");
            return;
        }

        if (!validarRut(rut)) {
            System.out.println("El formato del rut no es válido");
            return;
        }

        if (getUsuarios().containsKey(email)) {
            System.out.println("Este correo electrónico ya está registrado. Por favor, inicie sesión.");
        } else {
            Usuario nuevoUsuario = new Usuario(nombre, apellido, rut, email, password);
            getUsuarios().put(email, nuevoUsuario);
            System.out.println("¡Usuario registrado correctamente!");

            String datosUsuario = """
                    _________________________________________ 
                                
                           ***  Datos del usuario  ***                        
                    _________________________________________ 
                  
                      Nombre: %s %s 
                      Rut: %-10s       
                      Email: %-10s         
                    _________________________________________ 
                    """.formatted(nombre, apellido,rut,email);
            System.out.println(datosUsuario);
        }
    }

    /**
     * Inicia sesión de un usuario existente.
     * @param scanner El objeto Scanner para la entrada del usuario.
     * @return El usuario que inició sesión, o null si la autenticación falla.
     */
    public Usuario iniciarSesion(Scanner scanner) {
        System.out.println("Inicio de sesión");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Usuario usuario = getUsuarios().get(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            System.out.println("¡Inicio de sesión exitoso!");
            return usuario;
        } else {
            return null;
        }
    }

    /**
     * Valida el formato de un correo electrónico.
     * @param email El correo electrónico a validar.
     * @return true si el formato es válido, false de lo contrario.
     */
    public boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    /**
     * Valida el formato de un RUT (Rol Único Tributario).
     * @param rut El RUT a validar.
     * @return true si el formato es válido, false de lo contrario.
     */
    public boolean validarRut(String rut) {
        String regex = "^\\d{1,2}\\.\\d{3}\\.\\d{3}[-][0-9kK]{1}$";
        return rut.matches(regex);
    }
}

