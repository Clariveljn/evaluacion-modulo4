package wallet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wallet.Cuenta;

public class CuentaTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	Cuenta cuenta;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
	}
	
	@Before
	public void setUp() {
		cuenta = new Cuenta(123123, "Clarivel Jeldres");	
	}
	
	

	@Test
	public void testRealizarDepositoCantidadValida() {
		String input = "2500\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		cuenta.realizarDeposito(new Scanner(System.in));

		assertEquals(2500, cuenta.getSaldo(), 0);
		assertTrue(outContent.toString().contains("¡Depósito realizado con éxito!"));
	}

	@Test
	public void testRealizarDepositoCantidadInvalida() {
		String input = "0\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		cuenta.realizarDeposito(new Scanner(System.in));

		assertTrue(outContent.toString().contains("La cantidad a depositar debe ser mayor a 0"));
	}

	@Test
	public void testDepositar() {
		cuenta.depositar(100.0);
		assertEquals(100.0, cuenta.getSaldo(), 0.001);
	}

	@Test
	public void testRealizarRetiroCantidadValida() {
		cuenta.setSaldo(10000);
		String input = "2500\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		
		cuenta.realizarRetiro(new Scanner(System.in));
		
		assertEquals(7500, cuenta.getSaldo(), 0);
		assertTrue(outContent.toString().contains("¡Retiro realizado con éxito!"));
	}
	


	@Test
	public void testRetirarSaldoInsuficiente() {
		cuenta.setSaldo(1000);

		try {
			cuenta.retirar(2000);
		} catch (IllegalArgumentException e) {
			assertEquals("Saldo insuficiente.", e.getMessage());
		}
	}

	@Test
	public void testRetirarSaldoMenorQueCero() {
		cuenta.setSaldo(1000);

		try {
			cuenta.retirar(0);
		} catch (IllegalArgumentException e) {
			assertEquals("La cantidad a retirar debe ser mayor a 0.", e.getMessage());
		}
	}

	@Test
	public void testRetirar() {
		cuenta.setSaldo(200.0);
		assertTrue(cuenta.retirar(100.0));
		assertEquals(100.0, cuenta.getSaldo(), 0.001);
	}
	
	@Test
	public void testRegistroCuentaUnica() {
		Set<Integer> cuentasRegistradas = Cuenta.getNumerosCuentaRegistrados();
		cuentasRegistradas.clear();
		Cuenta cuenta1 = new Cuenta(123456, "Clarivel Jeldres");
		Cuenta cuenta2 = new Cuenta(654321, "Alfredo Leal");
		
		assertTrue(cuentasRegistradas.contains(123456));
		assertTrue(cuentasRegistradas.contains(654321));
		assertEquals(2, cuentasRegistradas.size());
	}

	@Test
	public void testRegistroCuentaDuplicada() {
		Set<Integer> cuentasRegistradas = Cuenta.getNumerosCuentaRegistrados();
		cuentasRegistradas.clear();
		Cuenta cuenta1 = new Cuenta(12345678, "Juan Oh");
		assertFalse(new Cuenta(12345678, "Juan Oh").equals(cuenta1));
		assertEquals(1, cuentasRegistradas.size());
	}
}
