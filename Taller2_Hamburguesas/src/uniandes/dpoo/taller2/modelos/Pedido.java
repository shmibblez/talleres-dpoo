package uniandes.dpoo.taller2.modelos;

import java.util.ArrayList;

public class Pedido {
	private static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> itemsPedido;
	
	public Pedido(String nombreCliente, String direccionCliente) {
		this.idPedido = numeroPedidos;
		Pedido.numeroPedidos += 1;
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsPedido = new ArrayList<Producto>();
	}
	
	public int getNumeroPedidos() {
		return numeroPedidos;
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	
	public String getNombreCliente() {
		return nombreCliente;
	}
	
	public String getDireccionCliente() {
		return direccionCliente;
	}
	
	public ArrayList<Producto> getItemsPedido() {
		return itemsPedido;
	}
	
	public String generarTextoFactura() {
		String texto = "";
		for (Producto i : itemsPedido) {
			texto += i.generarTextoFactura() + "\n";
		}
		return texto;
	}
	
	public void agregarItem(Producto item) {
		itemsPedido.add(item);
	}
}
