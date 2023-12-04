package uniandes.dpoo.taller6.errores;

import uniandes.dpoo.taller6.modelos.Producto;

public class ValorExcesivoException extends HamburguesaException {

	private Producto p;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3516386331917056658L;

	public ValorExcesivoException(String mensaje, Producto p) {
		super(mensaje);
		this.p = p;
	}
	
	public Producto getProducto() {
		return this.p;
	}

}
