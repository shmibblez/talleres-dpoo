package uniandes.dpoo.taller6.modelos;

public class ProductoMenu implements Producto {
	
	private int precio;
	private String nombre;
	
	public ProductoMenu(String nombre, int precio) {
		this.nombre = nombre;
		this.precio = precio;
	}
	
	public int getPrecio() {
		return precio;
	}
	public String getNombre() {
		return nombre;
	}
	public String generarTextoFactura() {
		return nombre + " ($" + precio + ")";
	}
}
