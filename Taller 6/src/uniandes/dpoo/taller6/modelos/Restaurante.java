package uniandes.dpoo.taller6.modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Restaurante {
	private Pedido pedidoEnCurso;
	private HashMap<Integer, Pedido> pedidos;
	private HashMap<String, Ingrediente> ingredientes;
	private HashMap<String, Combo> combos;
	private HashMap<String, ProductoMenu> menuBase;
	
	public Restaurante(HashMap<String, Ingrediente> ingredientes, HashMap<String, ProductoMenu> menuBase, HashMap<String, Combo> combos) {
		this.ingredientes = ingredientes;
		this.combos = combos;
		this.menuBase = menuBase;
		this.pedidoEnCurso = null;
		this.pedidos = new HashMap<Integer, Pedido>();
	}
	
	public Pedido getPedidoEnCurso() {
		return this.pedidoEnCurso;
	}
	
	public HashMap<Integer, Pedido> getPedidos() {
		return this.pedidos;
	}
	
	public HashMap<String, Ingrediente> getIngredientes() {
		return this.ingredientes;
	}
	
	public HashMap<String, Combo> getCombos() {
		return this.combos;
	}
	
	public HashMap<String, ProductoMenu> getMenuBase() {
		return this.menuBase;
	}
	
	public void printMenuBase() {
		for (ProductoMenu i :this.menuBase.values()) {
			System.out.println(i.getNombre() + ": $" + i.getPrecio());
		}
	}
	
	public void printPedidos() {
		for (Pedido p : this.pedidos.values()) {
			printPedido(p);
		}
	}
	
	public void printPedido(Pedido p) {
		System.out.println("Pedido #" + p.getIdPedido() + " para " + p.getNombreCliente() + " a " + p.getDireccionCliente());
		for (Producto r : p.getItemsPedido()) {
			System.out.println("- " + r.generarTextoFactura());
		}
	}
	
	public void printIngredientesSinCosto() {
		for (Ingrediente i : ingredientes.values()) {
			System.out.println("- " + i.getNombre());
		}
	}
	
	public void printIngredientesConCosto() {
		for (Ingrediente i : ingredientes.values()) {
			System.out.println("- " + i.getNombre() + " ($" + i.getCostoAdicional() + ")");
		}
	}
	
	public Pedido iniciarPedido(String nombreCliente, String direccion) {
		Pedido p = new Pedido(nombreCliente, direccion);
		this.pedidoEnCurso = p;
		pedidos.put(p.getIdPedido(), p);
		return p;
	}
	
	public void removerPedido(int i) {
		pedidos.remove(i);
	}
	
	public void agregarItemPedido(int id, Producto item) {
		Pedido p = pedidos.get(id);
		if (p!= null) {
			p.agregarProducto(item);
		}
	}
}
