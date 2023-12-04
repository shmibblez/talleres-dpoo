package uniandes.dpoo.taller6.errores;

public abstract class HamburguesaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3793414877727265113L;

	public HamburguesaException(String mensaje) {
		super(mensaje);
	}
}
