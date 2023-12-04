package uniandes.dpoo.taller6.modelos;

import java.util.ArrayList;

public class ProductoAjustado extends ProductoMenu {
	
	private ArrayList<Ingrediente> agregados;
	private ArrayList<Ingrediente> eliminados;
	
	public ProductoAjustado(String nombre, int precio, ArrayList<Ingrediente> agregados, ArrayList<Ingrediente> eliminados) {
		super(nombre, precio);
		this.agregados = agregados;
		this.eliminados = eliminados;
	}

	@Override
	public int getPrecio() {
		int precioTotal = super.getPrecio();
		
		for(Ingrediente i: agregados) {
			precioTotal += i.getCostoAdicional();
		}
		
		return precioTotal;
	}

	@Override
	public String getNombre() {
		String nombre = super.getNombre();
		for (Ingrediente i: agregados) {
			nombre += " con adicion de " + i.getNombre();
		}
		for (Ingrediente i: eliminados) {
			nombre += " sin " + i.getNombre();
		}
		return nombre;
	}

	@Override
	public String generarTextoFactura() {
		String texto = super.getNombre() + " ($" + getPrecio() + ")";
		for (Ingrediente i: agregados) {
			texto += "\n+ " + i.getNombre() + " ($+" + i.getCostoAdicional() + ")";
		}
		for (Ingrediente i: eliminados) {
			texto += "\n- " + i.getNombre();
		}
		return texto;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Producto) {
			return ((Producto) obj).getNombre().equals(super.getNombre());
		}
		return false;
	}
	
}
