package wallet;

/**
 * La clase Peso implementa la interfaz Moneda para representar la moneda Peso
 * Chileno.
 */
public class Peso implements Moneda {

	@Override
	public String getSimbolo() {
		return "$";
	}

	@Override
	public double getFactorConversion() {
		return 1.0;
	}

	/**
	 * Convierte una cantidad dada de dinero de Peso Chileno a la moneda base del
	 * sistema.
	 * 
	 * @param cantidad La cantidad de dinero en Peso Chileno a convertir.
	 * @return La cantidad convertida a la moneda base del sistema.
	 */
	@Override
	public double convertir(double cantidad) {
		return cantidad * getFactorConversion();
	}

	@Override
	public String getCodigo() {
		return "CLP";
	}
}
