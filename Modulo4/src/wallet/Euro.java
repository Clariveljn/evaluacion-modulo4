package wallet;

/**
 * La clase Euro implementa la interfaz Moneda para representar la moneda Euro.
 */
public class Euro implements Moneda {
    @Override
    public String getSimbolo() {
        return "€";
    }

    @Override
    public double getFactorConversion() {
        return 0.00099;
    }

    /**
     * Convierte una cantidad dada de dinero de Euro a la moneda base del sistema.
     * @param cantidad La cantidad de dinero en Euro a convertir.
     * @return La cantidad convertida a la moneda base del sistema.
     */
    @Override
    public double convertir(double cantidad) {
        return cantidad * getFactorConversion();
    }
    
    @Override
    public String getCodigo() {
        return "EUR";
    }
}
