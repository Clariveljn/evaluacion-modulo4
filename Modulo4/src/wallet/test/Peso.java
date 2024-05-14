package wallet.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wallet.Dolar;
import wallet.Peso;

class PesoTest {
	
	Peso peso;
	
	 @BeforeEach
	    public void setUp() {
		 peso = new Peso();    
	    }


	@Test
	void testGetSimbolo() {	
	    assertEquals("$", peso.getSimbolo());
	}


	@Test
	void testGetFactorConversion() {
		assertEquals(1, peso.getFactorConversion());
	}
		

	@Test
	void testConvertir() {
		double cantidad = 1000;
		
		assertEquals(1000, peso.convertir(cantidad));
	}

}
