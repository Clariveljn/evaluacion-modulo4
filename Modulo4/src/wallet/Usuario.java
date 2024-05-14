package wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La clase Usuario representa a un usuario del sistema, que puede tener una o varias cuentas.
 */
public class Usuario {
    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private String password;
    private List<Cuenta> cuentas;

    /**
     * Constructor de la clase Usuario.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param rut El RUT del usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public Usuario(String nombre, String apellido, String rut, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.email = email;
        this.password = password;
        this.cuentas = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    String menuOperaciones = """
            ________________________________

             *** Selecciona una opción ***
            ________________________________

            1. Crear cuenta
            2. Depositar
            3. Retirar
            4. Ver saldo
            5. Salir
            ________________________________

            Ingrese su opción: """;

    /**
     * Muestra el menú de operaciones y permite al usuario interactuar con su cuenta.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void menuOperaciones(Scanner scanner) {
        while (true) {
            System.out.println(menuOperaciones);

            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        crearCuenta(scanner);
                        break;
                    case 2:
                        depositar(scanner);
                        break;
                    case 3:
                        retirar(scanner);
                        break;
                    case 4:
                        verSaldo(scanner);
                        break;
                    case 5:
                        System.out.println("¡Hasta luego!");
                        System.exit(0);
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } else {
                scanner.next();
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    /**
     * Crea una nueva cuenta asociada al usuario.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void crearCuenta(Scanner scanner) {
        if (cuentas == null) {
            cuentas = new ArrayList<>();
        }
        System.out.println("Creación de cuenta");
        System.out.print("Número de cuenta: ");
        int numeroCuenta = scanner.nextInt();

        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta() == (numeroCuenta)) {
                System.out.println("Ya existe una cuenta con ese número. No se puede crear la cuenta.");
                return;
            }
        }

        Cuenta cuenta = new Cuenta(numeroCuenta, nombre + " " + apellido);
        cuentas.add(cuenta);
        System.out.println("¡Cuenta creada exitosamente!");

        String detalleCuenta = """
                _________________________________________

                     *** Detalles de la cuenta ***
                _________________________________________

                     Titular: %s %s
                     Rut del titular: %s
                     Número de cuenta: %s
                     Saldo disponible: $ %s CLP
                _________________________________________
                            """.formatted(nombre, apellido, rut, cuenta.getNumeroCuenta(), cuenta.getSaldo());

        System.out.println(detalleCuenta);
    }

    /**
     * Realiza un depósito en una cuenta del usuario.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void depositar(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta: ");
        if (cuentas == null || cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas. Por favor, registre una cuenta antes de realizar un depósito.");
            return;
        }

        int numeroCuenta = scanner.nextInt();
        scanner.nextLine();

        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            cuenta.realizarDeposito(scanner);
        } else {
            System.out.println("La cuenta no existe.");
        }
    }

    /**
     * Realiza un retiro desde una cuenta del usuario.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void retirar(Scanner scanner) {
        if (cuentas == null || cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas. Por favor, registre una cuenta antes de realizar un retiro.");
            return;
        }
        System.out.print("Ingrese el número de cuenta: ");
        int numeroCuenta = scanner.nextInt();
        scanner.nextLine();

        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            cuenta.realizarRetiro(scanner);
        } else {
            System.out.println("La cuenta no existe.");
        }
    }

    /**
     * Busca una cuenta por su número.
     * @param numeroCuenta El número de cuenta a buscar.
     * @return La cuenta encontrada, o null si no se encuentra.
     */
    public Cuenta buscarCuenta(int numeroCuenta) {
        if (cuentas != null && !cuentas.isEmpty()) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getNumeroCuenta() == numeroCuenta) {
                    return cuenta;
                }
            }
        }
        return null; 
    }

    /**
     * Muestra el saldo de una cuenta en una moneda específica.
     * @param cuenta, La cuenta de la que se desea ver el saldo.
     * @param moneda, La moneda en la que se desea ver el saldo.
     */
    public void mostrarSaldoEnMoneda(Cuenta cuenta, Moneda moneda) {
        double saldo = moneda.convertir(cuenta.getSaldo());
        String simbolo = moneda.getSimbolo();
        String codigoMoneda = moneda.getCodigo();
        System.out.printf("Saldo disponible: %s %.2f %s%n", simbolo, saldo, codigoMoneda);
    }

    /**
     * Muestra el saldo de una cuenta en diferentes monedas.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void verSaldo(Scanner scanner) {
        System.out.print("Ingrese el número de cuenta: ");
        if (cuentas == null || cuentas.isEmpty()) {
        	System.out.println("No hay cuentas creadas.");
			return;
		}

		int numeroCuenta = scanner.nextInt();
		scanner.nextLine();
		Cuenta cuenta = buscarCuenta(numeroCuenta);

		if (cuenta != null) {
			System.out.println("Seleccione la moneda para ver el saldo:");
			System.out.println("1. Pesos chilenos");
			System.out.println("2. Dólares");
			System.out.println("3. Euros");
			System.out.print("Ingrese su elección: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();
			switch (opcion) {
			case 1:
				mostrarSaldoEnMoneda(cuenta, new Peso());
				break;
			case 2:
				mostrarSaldoEnMoneda(cuenta, new Dolar());
				break;
			case 3:
				mostrarSaldoEnMoneda(cuenta, new Euro());
				break;
			default:
				System.out.println("Opción inválida.");
				return;
			}
		} else {
			System.out.println("La cuenta no existe.");
		}
	}
}