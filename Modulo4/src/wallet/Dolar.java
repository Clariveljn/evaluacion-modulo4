package wallet;

/**
 * La clase Dolar implementa la interfaz Moneda para representar la moneda Dólar.
 */
public class Dolar implements Moneda {

    @Override
    public String getSimbolo() {
        return "$";
    }

    @Override
    public double getFactorConversion() {
        return 0.0011; 
    }

    /**
     * Convierte una cantidad dada de dinero de Dólar a la moneda base del sistema.
     * @param cantidad La cantidad de dinero en Dólar a convertir.
     * @return La cantidad convertida a la moneda base del sistema.
     */
    @Override
    public double convertir(double cantidad) {
        return cantidad * getFactorConversion();
    }
    
    @Override
    public String getCodigo() {
        return "USD";
    }
}
