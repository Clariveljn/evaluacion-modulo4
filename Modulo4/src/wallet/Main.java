package wallet;

import java.util.Scanner;

/**
 * La clase Main contiene el método principal que inicia la aplicación de billetera digital.
 */
public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Autenticacion autenticacion = new Autenticacion();

		String menuPrincipal = """
				
				___________________________________________

				      *** Selecciona una opción ***
				___________________________________________

				    1. Registrarse
				    2. Iniciar sesión
				    3. Salir
				___________________________________________
				   Ingrese su opción: """;

		while (true) {
			System.out.println(menuPrincipal);

			if (scanner.hasNextInt()) {

				int opcion = scanner.nextInt();
				scanner.nextLine();
				switch (opcion) {
				case 1:
					autenticacion.registrarUsuario(scanner);
					break;
				case 2:
					Usuario usuario = autenticacion.iniciarSesion(scanner);
					if (usuario != null) {
						usuario.menuOperaciones(scanner);
					} else {
						System.out.println("Inicio de sesión fallido. Intente nuevamente.");
					}
					break;
				case 3:
					System.out.println("¡Hasta luego!");
					System.exit(0);
				default:
					System.out.print("Opción inválida. Intente nuevamente.");
				}
			} else {
				scanner.next();
				System.out.println("Por favor, ingrese un número válido.");
			}
		}
	}
}
