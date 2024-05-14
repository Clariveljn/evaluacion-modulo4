package wallet.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wallet.Euro;

class EuroTest {

	Euro euro;

	@BeforeEach
	public void setUp() {
		euro = new Euro();
	}

	@Test
	void testGetSimbolo() {
		assertEquals("€", euro.getSimbolo());

	}

	@Test
	void testGetFactorConversion() {
		assertEquals(0.00099, euro.getFactorConversion());
	}

	@Test
	void testConvertir() {
		double cantidad = 5000;

		assertEquals(4.95, euro.convertir(cantidad));
	}

}
