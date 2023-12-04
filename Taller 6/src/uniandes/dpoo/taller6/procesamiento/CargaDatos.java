package uniandes.dpoo.taller6.procesamiento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import uniandes.dpoo.taller6.errores.IngredienteRepetidoException;
import uniandes.dpoo.taller6.errores.ProductoRepetidoException;
import uniandes.dpoo.taller6.modelos.Combo;
import uniandes.dpoo.taller6.modelos.Ingrediente;
import uniandes.dpoo.taller6.modelos.Producto;
import uniandes.dpoo.taller6.modelos.ProductoMenu;
import uniandes.dpoo.taller6.modelos.Restaurante;

public class CargaDatos {
	public static Restaurante cargarArchivos(String ingredientesPath, String combosPath, String menuPath) throws IOException {
		HashMap<String, Ingrediente> ingredientes = cargarIngredientes(ingredientesPath);
		HashMap<String, ProductoMenu> menuBase = cargarMenu(menuPath);
		HashMap<String, Combo> combos = cargarCombos(combosPath, menuBase);
		return new Restaurante(ingredientes, menuBase, combos);
	}
	
	private static HashMap<String, Ingrediente> cargarIngredientes(String path) throws IOException, IngredienteRepetidoException {
		HashMap<String, Ingrediente> ingredientes = new HashMap<String, Ingrediente>();
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
		BufferedReader br = new BufferedReader(new FileReader(path));
		String linea = br.readLine();
		while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
		{
			// Separar los valores que estaban en una línea
			String[] partes = linea.split(";");
			String nombre = partes[0];
			Integer costoAdicional = Integer.parseInt(partes[1]);
			if(ingredientes.containsKey(nombre)) {
				throw new IngredienteRepetidoException("ingrediente repetido en carga de datos, debe estar repetido en el archivo de datos");
			}
			ingredientes.put(nombre, new Ingrediente(nombre, costoAdicional));
			// Leer siguiente linea
			linea = br.readLine();
		}
		
		br.close();
		return ingredientes;
	}
	
	private static HashMap<String, ProductoMenu> cargarMenu(String path) throws IOException, ProductoRepetidoException {
		HashMap<String, ProductoMenu> productos = new HashMap<String, ProductoMenu>();
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
		BufferedReader br = new BufferedReader(new FileReader(path));
		String linea = br.readLine();
		while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
		{
			// Separar los valores que estaban en una línea
			String[] partes = linea.split(";");
			String nombre = partes[0];
			Integer costo = Integer.parseInt(partes[1]);
			if(productos.containsKey(nombre)) {
				throw new ProductoRepetidoException("producto repetido en carga de datos, debe estar repetido en el archivo de datos");

			}
			productos.put(nombre, new ProductoMenu(nombre, costo));
			// Leer siguiente linea
			linea = br.readLine();
		}
		
		br.close();
		return productos;
	}
	
	private static HashMap<String, Combo> cargarCombos(String path, HashMap<String, ProductoMenu> menu) throws IOException {
		HashMap<String, Combo> combo = new HashMap<String, Combo>();
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
		BufferedReader br = new BufferedReader(new FileReader(path));
		String linea = br.readLine();
		while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
		{
			List<String> partes = Arrays.asList(linea.split(";"));
			Iterator<String> it = partes.iterator();
			String nombre = it.next();
			String descuentoStr = it.next();
			// remueve simbolo de porcentaje y convierte a decimal
			double descuento = Double.parseDouble(descuentoStr.substring(0,descuentoStr.length()-1)) / 100.0;
			ArrayList<Producto> productos = new ArrayList<Producto>();
			while (it.hasNext()) {
				String itemMenu = it.next();
				productos.add(menu.get(itemMenu));
			}
			combo.put(nombre, new Combo(nombre, descuento, productos));
			// Leer siguiente linea
			linea = br.readLine();
		}
		
		br.close();
		return combo;
	}
}
