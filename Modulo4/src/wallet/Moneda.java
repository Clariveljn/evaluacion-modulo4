package wallet;

/**
 * La interfaz Moneda proporciona métodos para obtener información sobre una moneda y realizar conversiones.
 */
interface Moneda {
    String getSimbolo();
    double getFactorConversion();
    double convertir(double cantidad);
    String getCodigo();
}

