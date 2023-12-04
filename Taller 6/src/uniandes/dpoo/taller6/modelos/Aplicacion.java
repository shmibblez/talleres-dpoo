package uniandes.dpoo.taller6.modelos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import uniandes.dpoo.taller6.procesamiento.CargaDatos;

public class Aplicacion {
	private Restaurante restaurante;
	
	public Aplicacion(String ingredientesPath, String combosPath, String menuPath) throws IOException {
		this.restaurante = CargaDatos.cargarArchivos(ingredientesPath, combosPath, menuPath);
	}
	
	public Restaurante getRestaurante() {
		return this.restaurante;
	}
	
	private boolean continuarAplicacion = true;
	
	private void mostrarOpciones() {
		System.out.println("Bienvenido! Escoge de las siguientes opciones:");
		System.out.println("1. Mostrar menu");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Agregar un elemento a un pedido existente");
		System.out.println("4. Cerrar un pedido y guardar la factura");
		System.out.println("5. Consultar la informacion de un pedido");
		System.out.println("6. Cerrar aplicacion");
	}
	
	private void desplegarOpcion(int opcion) {
		switch (opcion) {
		case 1:
			mostrarMenu();
			return;
		case 2:
			iniciarNuevoPedido();
			return;
		case 3:
			agregarElementoAPedido();
			return;
		case 4:
			cerrarPedido();
			return;
		case 5:
			consultarInfoPedido();
			return;
		case 6:
			continuarAplicacion = false;
			return;
		}
		System.out.println("Esto no deberia correr");
	}
	
	private void mostrarMenu() {
		getRestaurante().printMenuBase();
	}
	
	private void iniciarNuevoPedido() {
		String nombre = solicitarString("Ingresar nombre cliente: ");
		String direccion = solicitarString("Ingresar direccion: ");
		int p = this.getRestaurante().iniciarPedido(nombre, direccion).getIdPedido();
		System.out.println("pedido # " + p + " creado, items menu: ");
		selectorMenu(p);
	}
	
	private void selectorMenu(int p) {
		getRestaurante().printMenuBase();
		String i = solicitarString("seleccionar item a agregar: ");
		while (i == null || !getRestaurante().getMenuBase().containsKey(i) ) {
			i = solicitarString("Por favor, seleccionar un item valido: ");
		}
		Producto item = getRestaurante().getMenuBase().get(i);
		ArrayList<Ingrediente> agregarIngs = new ArrayList<Ingrediente>();
		ArrayList<Ingrediente> removerIngs = new ArrayList<Ingrediente>();
		String agregar = solicitarString("Deseas agregar ingredientes? (si/no)");
		while (!agregar.equals("si") && !agregar.equals("no")) {
			agregar = solicitarString("Por favor, seleccionar \"si\" o \"no\"");
		}
		if(agregar.equals("si")) {
			String seguirAgregando = "si";
			while(seguirAgregando.equals("si")) {
				getRestaurante().printIngredientesConCosto();
				String ingrediente = solicitarString("seleccionar ingrediente: ");
				while (ingrediente == null || !getRestaurante().getIngredientes().containsKey(ingrediente)) {
					ingrediente = solicitarString("Por favor, elegir ingrediente valido: ");
				}
				Ingrediente ing = getRestaurante().getIngredientes().get(ingrediente);
				if (!agregarIngs.contains(ing)) {
					agregarIngs.add(ing);
				}
				System.out.print(ingrediente + " agregado");
				seguirAgregando = solicitarString("Deseas agregar mas ingredientes? (si/no)");
				while (!seguirAgregando.equals("si") && !seguirAgregando.equals("no")) {
					seguirAgregando = solicitarString("Por favor, seleccionar \"si\" o \"no\"");
				}
			}
		}
		String remover = solicitarString("Deseas remover ingredientes? (si/no)");
		while (!remover.equals("si") && !remover.equals("no")) {
			remover = solicitarString("Por favor, seleccionar \"si\" o \"no\"");
		}
		if(remover.equals("si")) {
			String seguirRemoviendo = "si";
			while(seguirRemoviendo.equals("si")) {
				getRestaurante().printIngredientesSinCosto();
				String ingrediente = solicitarString("seleccionar ingrediente: ");
				while (ingrediente == null || !getRestaurante().getIngredientes().containsKey(ingrediente)) {
					ingrediente = solicitarString("Por favor, elegir ingrediente valido: ");
				}
				Ingrediente ing = getRestaurante().getIngredientes().get(ingrediente);
				if (!removerIngs.contains(ing)) {
					removerIngs.add(ing);
				}
				System.out.print(ingrediente + " removido");
				seguirRemoviendo = solicitarString("Deseas remover mas ingredientes? (si/no)");
				while (!seguirRemoviendo.equals("si") && !seguirRemoviendo.equals("no")) {
					seguirRemoviendo = solicitarString("Por favor, seleccionar \"si\" o \"no\"");
				}
			}
			if (agregarIngs.size() > 0 || removerIngs.size() > 0) {
				getRestaurante().agregarItemPedido(p, new ProductoAjustado(item.getNombre(), item.getPrecio(), agregarIngs, removerIngs));
			} else {
				getRestaurante().agregarItemPedido(p, new ProductoMenu(item.getNombre(), item.getPrecio()));
			}
		}
	}
	
	private void agregarElementoAPedido() {
		System.out.println("Pedidos existentes: ");
		getRestaurante().printPedidos();
		Integer p = solicitarNumero("seleccionar pedido: ");
		while (p == null || !getRestaurante().getPedidos().containsKey(p)) {
			p = solicitarNumero("Por favor, seleccionar un pedido valido: ");
		}
		System.out.println("Pedido #" + p + "seleccionado, items menu: ");
		selectorMenu(p);
	}
	
	private void cerrarPedido() {
		System.out.println("Pedidos existentes: ");
		getRestaurante().printPedidos();
		Integer p = solicitarNumero("seleccionar pedido: ");
		while (p == null || !getRestaurante().getPedidos().containsKey(p)) {
			p = solicitarNumero("Por favor, seleccionar un pedido valido: ");
		}
		System.out.println("Cerrando pedido #" + p + "...");
		//
		File pedidosCerrados = new File("./pedidosCerrados");
		if (!pedidosCerrados.exists()) {
			pedidosCerrados.mkdirs();
		}
		File pedido = new File("./pedidosCerrados/pedido-" + p + ".txt");
		try {
			pedido.createNewFile();
			FileWriter writer = new FileWriter(pedido.getPath());
			writer.write(getRestaurante().getPedidos().get(p).generarTextoFactura());
			getRestaurante().removerPedido(p);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se logro cerrar el pedido.");
		}
		
	}
	
	private void consultarInfoPedido() {
		System.out.println("Pedidos existentes: ");
		getRestaurante().printPedidos();
		Integer p = solicitarNumero("seleccionar pedido: ");
		while (p == null || !getRestaurante().getPedidos().containsKey(p)) {
			p = solicitarNumero("Por favor, seleccionar un pedido valido: ");
		}
		System.out.println("Info pedido #" + p + ":\n" + getRestaurante().getPedidos().get(p).generarTextoFactura());
	}
	
	private Integer solicitarNumero(String mensaje) {
		try
		{
			System.out.print(mensaje);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return Integer.parseInt(reader.readLine());
		}
		catch (Exception e)
		{
//				System.out.println("Error leyendo de la consola");
//				e.printStackTrace();
//				System.out.println();
		}
		return null;
		
	}
	
	private String solicitarString(String mensaje) {
		try
		{
			System.out.print(mensaje);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
//				System.out.println("Error leyendo de la consola");
//				e.printStackTrace();
//				System.out.println();
		}
		return null;
	}
	
	public void ejecutarAplicacion() {
		continuarAplicacion = true;
		while (continuarAplicacion) {
			mostrarOpciones();
			Integer opcion = solicitarNumero("Por favor selecciona una opcion:");
			if (opcion == null || opcion < 1 || opcion > 6 ) {
				System.out.println("Opcion invalida seleccionada");
				continue;
			}
			desplegarOpcion(opcion);
		}
	}
	
	public static void main(String[] args)
	{
		try {
			Aplicacion consola = new Aplicacion("./data/ingredientes.txt", "./data/combos.txt", "./data/menu.txt");
			consola.ejecutarAplicacion();
		} catch (IOException e) {
			System.out.println("La aplicacion fallo inesperadamente");
		}
	}
	
}
