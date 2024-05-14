package wallet;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * La clase Cuenta representa una cuenta bancaria con operaciones básicas como depósito y retiro.
 */
public class Cuenta {
    private static Set<Integer> numerosCuentaRegistrados = new HashSet<>();
    private int numeroCuenta;
    private double saldo;
    private String tipoMoneda;
    private String titular;

    /**
     * Constructor de la clase Cuenta.
     * @param numeroCuenta El número de cuenta.
     * @param titular El titular de la cuenta.
     */
    public Cuenta(int numeroCuenta, String titular) {
        if (numerosCuentaRegistrados.contains(numeroCuenta)) {
            System.out.println("Esta cuenta ya ha sido registrada");
            return;
        }

        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.tipoMoneda = "CLP";

        numerosCuentaRegistrados.add(numeroCuenta);
    }

    /**
     * Obtiene el conjunto de números de cuenta registrados.
     * @return El conjunto de números de cuenta registrados.
     */
    public static Set<Integer> getNumerosCuentaRegistrados() {
        return numerosCuentaRegistrados;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Realiza un depósito en la cuenta.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void realizarDeposito(Scanner scanner) {
        System.out.print("Ingrese la cantidad a depositar: ");
        double cantidad = scanner.nextDouble();

        try {
            if (cantidad <= 0) {
                throw new Exception("La cantidad a depositar debe ser mayor a 0");
            }
            depositar(cantidad);
            System.out.println("¡Depósito realizado con éxito!");
            System.out.println("Tu saldo actual es: $ " + saldo + " CLP");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Realiza un depósito en la cuenta.
     * @param cantidad La cantidad a depositar.
     */
    public void depositar(double cantidad) {
        saldo += cantidad;  
    }

    /**
     * Realiza un retiro de la cuenta.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public void realizarRetiro(Scanner scanner) {
        System.out.print("Ingrese la cantidad a retirar: ");
        double cantidad = scanner.nextDouble();
        scanner.nextLine();
        
        if (retirar(cantidad)) {
            System.out.println("¡Retiro realizado con éxito!");
            System.out.println("Tu saldo actual es: $ " + saldo + " CLP");
        }
    }

    /**
     * Realiza un retiro de la cuenta.
     * @param cantidad La cantidad a retirar.
     * @return true si el retiro fue exitoso, false de lo contrario.
     */
    public boolean retirar(double cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad a retirar debe ser mayor a 0.");
            return false;
        }
        if (saldo < cantidad) {
            System.out.println("Saldo insuficiente.");
            return false;
        }
        saldo -= cantidad;
        return true;
    }
}

