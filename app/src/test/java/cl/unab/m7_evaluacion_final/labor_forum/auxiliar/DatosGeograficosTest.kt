package cl.unab.m7_evaluacion_final.labor_forum.auxiliar

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DatosGeograficosTest {

    @Test
    fun obtenerComunasPorRegion_regionValida_retornaListaNoVacia() {
        // GIVEN: Una región válida
        val region = "Región Metropolitana de Santiago"

        // WHEN: Solicitamos las comunas
        val comunas = DatosGeograficos.obtenerComunasPorRegion(region)

        // THEN: La lista no debe estar vacía y debe contener "Santiago"
        assertTrue("La lista de comunas no debería estar vacía", comunas.isNotEmpty())
        assertTrue("La lista debería contener Santiago", comunas.contains("Santiago"))
    }

    @Test
    fun obtenerComunasPorRegion_regionInvalida_retornaListaVacia() {
        // GIVEN: Una región inexistente
        val region = "Región de Narnia"

        // WHEN: Solicitamos las comunas
        val comunas = DatosGeograficos.obtenerComunasPorRegion(region)

        // THEN: La lista debe estar vacía (manejado por el 'else' del when)
        assertTrue("La lista debería estar vacía para regiones desconocidas", comunas.isEmpty())
    }
}