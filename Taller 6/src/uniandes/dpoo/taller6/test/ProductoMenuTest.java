package uniandes.dpoo.taller6.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller6.modelos.Aplicacion;
import uniandes.dpoo.taller6.modelos.ProductoMenu;
import uniandes.dpoo.taller6.modelos.Restaurante;
import uniandes.dpoo.taller6.procesamiento.CargaDatos;

class ProductoMenuTest {
	
	private ProductoMenu productoMenu;

	@BeforeEach
	void setUp() throws Exception {
		Restaurante r = CargaDatos.cargarArchivos(Aplicacion.ingredientesPath, Aplicacion.combosPath, Aplicacion.menuPath);
		productoMenu = r.getMenuBase().values().iterator().next();
	}


	@Test
	void testGenerarTextoFactura() {
		assertNotEquals("", productoMenu.generarTextoFactura());
		assertNotEquals(null, productoMenu.generarTextoFactura());
	}

}
