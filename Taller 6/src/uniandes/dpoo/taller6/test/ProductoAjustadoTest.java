package uniandes.dpoo.taller6.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller6.modelos.Aplicacion;
import uniandes.dpoo.taller6.modelos.Ingrediente;
import uniandes.dpoo.taller6.modelos.ProductoAjustado;
import uniandes.dpoo.taller6.modelos.ProductoMenu;
import uniandes.dpoo.taller6.modelos.Restaurante;
import uniandes.dpoo.taller6.procesamiento.CargaDatos;

class ProductoAjustadoTest {
	
	ProductoAjustado productoAjustado;

	@BeforeEach
	void setUp() throws Exception {
		Restaurante r = CargaDatos.cargarArchivos(Aplicacion.ingredientesPath, Aplicacion.combosPath, Aplicacion.menuPath);
		ProductoMenu p = r.getMenuBase().values().iterator().next();
		Iterator<Ingrediente> ingredientes = r.getIngredientes().values().iterator();
		ArrayList<Ingrediente> agregados = new ArrayList<Ingrediente>(Arrays.asList(ingredientes.next()));
		ArrayList<Ingrediente> eliminados = new ArrayList<Ingrediente>(Arrays.asList(ingredientes.next()));
		productoAjustado = new ProductoAjustado(p.getNombre(),p.getPrecio(),agregados,eliminados);
	}
	
	@Test
	void testGenerarTextoFactura() {
		assertNotEquals("", productoAjustado.generarTextoFactura());
		assertNotEquals(null, productoAjustado.generarTextoFactura());
	}

}
