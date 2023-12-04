package uniandes.dpoo.taller6.modelos;

import java.util.ArrayList;

import uniandes.dpoo.taller6.errores.ValorExcesivoException;

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
			System.out.print("Pedido.generarTextoFactura");
			texto += i.generarTextoFactura() + "\n";
		}
		return texto;
	}
	
	public void agregarProducto(Producto p) throws ValorExcesivoException {
		if(p.getPrecio() > 150000) {
			throw new ValorExcesivoException("valor de producto es excesivo, valor maximo permitido: 150000, valor item actual: " + p.getPrecio(), p);
		}
		itemsPedido.add(p);
	}
}
