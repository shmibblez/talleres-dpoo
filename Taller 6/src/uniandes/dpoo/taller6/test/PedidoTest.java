package uniandes.dpoo.taller6.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller6.modelos.Aplicacion;
import uniandes.dpoo.taller6.modelos.Pedido;
import uniandes.dpoo.taller6.modelos.ProductoMenu;
import uniandes.dpoo.taller6.modelos.Restaurante;
import uniandes.dpoo.taller6.procesamiento.CargaDatos;

class PedidoTest {
	
	private Pedido pedido;
	private Restaurante r;

	@BeforeEach
	void setUp() throws Exception {
		this.r = CargaDatos.cargarArchivos(Aplicacion.ingredientesPath, Aplicacion.combosPath, Aplicacion.menuPath);
		
		pedido = new Pedido("Joe", "25 Joe Street");
	}

	@Test
	void testGenerarTextoFactura() {
		assertNotEquals("", pedido.generarTextoFactura());
		assertNotEquals(null, pedido.generarTextoFactura());
	}

	@Test
	void testAgregarProducto() {
		ProductoMenu p = this.r.getMenuBase().values().iterator().next();
		assertDoesNotThrow( () -> pedido.agregarProducto(p), "");
	}

}
