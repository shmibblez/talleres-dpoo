package uniandes.dpoo.taller2.modelos;

import java.util.ArrayList;

public class Combo implements Producto {
	private double descuento;
	private String nombreCombo;
	private ArrayList<Producto> itemsCombo;
	
	public Combo(String nombreCombo, double descuento, ArrayList<Producto> itemsCombo) {
		this.descuento = descuento;
		this.nombreCombo = nombreCombo;
		this.itemsCombo = itemsCombo;
	}
	
	@Override
	public int getPrecio() {
		int total = 0;
		for (Producto i: itemsCombo) {
			total += i.getPrecio();
		}
		return (int) Math.round(total * (1.0 - descuento));
	}
	@Override
	public String getNombre() {
		return nombreCombo;
	}
	@Override
	public String generarTextoFactura() {
		String texto = getNombre() + " ($" + getPrecio() + ")";
		for (Producto i: itemsCombo) {
			texto += "\n* " + i.getNombre() + " ($" + i.getPrecio() + ")";
		}
		return texto;
	}
}
