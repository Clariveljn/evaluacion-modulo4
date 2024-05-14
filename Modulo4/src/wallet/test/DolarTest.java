package wallet.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wallet.Dolar;

class DolarTest {
	
	Dolar dolar;
	
	 @BeforeEach
	    public void setUp() {
		 dolar = new Dolar();    
	    }


	@Test
	void testGetSimbolo() {	
	    assertEquals("$", dolar.getSimbolo());
	}


	@Test
	void testGetFactorConversion() {
		assertEquals(0.0011, dolar.getFactorConversion());
	}
		

	@Test
	void testConvertir() {
		double cantidad = 1000;
		
		assertEquals(1.1, dolar.convertir(cantidad));
	}

}
