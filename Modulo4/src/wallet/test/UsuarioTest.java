package wallet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import wallet.Cuenta;
import wallet.Dolar;
import wallet.Euro;
import wallet.Peso;
import wallet.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioTest {
	private ByteArrayOutputStream outputStream;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayInputStream inputStreamCaptor = new ByteArrayInputStream("1\n".getBytes());
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private Scanner scanner;
	
	
	private Usuario usuario;

	private List<Cuenta> cuentas;
	
	@BeforeEach
	public void setUp1() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	    System.setIn(inputStreamCaptor);
	}

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@Before
	public void setUp() {
	    usuario = new Usuario("Juan", "Perez", "12345678-9", "juan.perez@email.com", "password123");
	    scanner = new Scanner(System.in);
	    outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	}

	@After
	public void tearDown() {
	    System.setOut(System.out);
	    outputStream.reset();
	    scanner.close();
	}



	@Test
	public void testCrearCuenta() {
		usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		cuentas = new ArrayList<>();
		String input = "123456\n";

		usuario.setCuentas(cuentas);

		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);
		usuario.crearCuenta(scanner);

		assertEquals(1, cuentas.size());

		cuentas.clear();

	}

	@Test
	public void testCrearCuentaExistente() {
		usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		cuentas = new ArrayList<>();

		String input = "444444\n";
		usuario.setCuentas(cuentas);

		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);
		usuario.crearCuenta(scanner);

		String input2 = "444444\n";
		usuario.setCuentas(cuentas);

		InputStream in2 = new ByteArrayInputStream(input2.getBytes());
		Scanner scanner2 = new Scanner(in2);
		usuario.crearCuenta(scanner2);

		int cuentasRegistradas = Cuenta.getNumerosCuentaRegistrados().size();
		System.out.println(Cuenta.getNumerosCuentaRegistrados());

		assertEquals(1, cuentas.size());
		assertTrue(outContent.toString().contains("Ya existe una cuenta con ese número. No se puede crear la cuenta."));
	}

	@Test
	public void testRetirar_CuentaExiste() {

		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		Cuenta cuenta = new Cuenta(1234, "Clarivel Jeldres");
		cuenta.depositar(100.0);
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(cuenta);
		usuario.setCuentas(cuentas);

		// Simular la entrada del usuario
		String input = "1234\n50\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);

		usuario.retirar(scanner);

		assertEquals(50.0, cuenta.getSaldo(), 0.1);
		cuentas.clear();
	}

	@Test
	public void testRetirar_SinCuentasRegistradas() {
		// Arrange
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		List<Cuenta> cuentas = new ArrayList<>();
		usuario.setCuentas(cuentas);

		// Simular la salida del sistema
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		usuario.retirar(new Scanner(System.in));

		assertTrue(outContent.toString()
				.contains("No hay cuentas registradas. Por favor, registre una cuenta antes de realizar un retiro."));
	}

	@Test
	public void testRetirar_CuentaNoExiste() {
		// Arrange
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		Cuenta cuenta = new Cuenta(12345, "Clarivel Jeldres");
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(cuenta);
		usuario.setCuentas(cuentas);

		String input = "54321\n50\n"; // Número de cuenta que no existe
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);

		// Act
		usuario.retirar(scanner);

		// Assert
		assertTrue(outContent.toString().contains("La cuenta no existe."));
	}

	@Test
	public void testDepositar_NoCuentasRegistradas() {
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		List<Cuenta> cuentas = new ArrayList<>();
		usuario.setCuentas(cuentas);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		usuario.depositar(new Scanner(System.in));

		assertTrue(outContent.toString()
				.contains("No hay cuentas registradas. Por favor, registre una cuenta antes de realizar un depósito."));
	}

	@Test
	public void testDepositar_CuentaNoExiste() {
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(new Cuenta(12345, "Clarivel"));
		usuario.setCuentas(cuentas);

		String input = "54321\n50\n"; // Número de cuenta que no existe
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		usuario.depositar(scanner);

		assertTrue(outContent.toString().contains("La cuenta no existe."));
	}

	@Test
	public void testDepositar_CuentaExiste() {
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		List<Cuenta> cuentas = new ArrayList<>();
		Cuenta cuenta = new Cuenta(12345, "Clarivel Jeldres");
		cuentas.add(cuenta);
		usuario.setCuentas(cuentas);

		String input = "12345\n50\n"; // Número de cuenta existente
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);

		usuario.depositar(scanner);

		assertEquals(50.0, cuenta.getSaldo(), 0.1);
	}

	@Test
	public void testVerSaldo_NoCuentasCreadas() {
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		usuario.setCuentas(new ArrayList<>());

		String input = "1\n"; // Número de cuenta existente
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		usuario.verSaldo(scanner);

		assertTrue(outContent.toString().contains("No hay cuentas creadas."));
	}

	@Test
	public void testVerSaldo_CuentaNoExiste() {
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(new Cuenta(12345, "Clarivel"));
		usuario.setCuentas(cuentas);

		String input = "54321\n1\n"; // Número de cuenta que no existe y opción 1 (pesos chilenos)
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Scanner scanner = new Scanner(in);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		usuario.verSaldo(scanner);

		assertTrue(outContent.toString().contains("La cuenta no existe."));
	}
	
	
	@Test
	public void testMostrarSaldoEnMoneda() {
		Usuario usuario = new Usuario("Clarivel", "Jeldres", "11.111.111-1", "clarivel@gmail.com", "clari123");
	    // Preparar datos de prueba
	    Cuenta cuenta = new Cuenta(12345, "Clarivel Jeldres");
	    cuenta.setSaldo(1000);

	    // Capturar la salida del método
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));

	    // Probar el método con diferentes monedas
	    usuario.mostrarSaldoEnMoneda(cuenta, new Peso());
	    String output1 = outputStream.toString().trim();
	    assertTrue(output1.contains("Saldo disponible: $ 1000.00 CLP") || output1.contains("Saldo disponible: $ 1000,00 CLP"));

	    outputStream.reset();
	    usuario.mostrarSaldoEnMoneda(cuenta, new Dolar());
	    String output2 = outputStream.toString().trim();
	    assertTrue(output2.contains("Saldo disponible: $ 1.10 USD") || output2.contains("Saldo disponible: $ 1,10 USD"));

	    outputStream.reset();
	    usuario.mostrarSaldoEnMoneda(cuenta, new Euro());
	    String output3 = outputStream.toString().trim();
	    assertTrue(output3.contains("Saldo disponible: € 0.99 EUR") || output3.contains("Saldo disponible: € 0,99 EUR"));
	}


}
