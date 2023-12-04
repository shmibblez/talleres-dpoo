package uniandes.dpoo.taller6.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller6.modelos.Aplicacion;
import uniandes.dpoo.taller6.modelos.Combo;
import uniandes.dpoo.taller6.modelos.Restaurante;
import uniandes.dpoo.taller6.procesamiento.CargaDatos;

class ComboTest {

	private Combo combo;
	
	@BeforeEach
	void setUp() throws Exception {
		Restaurante r = CargaDatos.cargarArchivos(Aplicacion.ingredientesPath, Aplicacion.combosPath, Aplicacion.menuPath);
		combo = r.getCombos().values().iterator().next();
	}

	@Test
	void testGenerarTextoFactura() {
		assertNotEquals("", combo.generarTextoFactura());
		assertNotEquals(null, combo.generarTextoFactura());
	}

}
