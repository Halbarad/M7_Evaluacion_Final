package cl.unab.m7_evaluacion_final.labor_forum.auxiliar

import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class OfertaSorterTest {

    @Test
    fun filtrarYOrdenarOfertas_priorizaComunaYRegion() {
        // GIVEN: Un usuario de "Santiago", "RM"
        val usuario = Usuario(
            id = 1, nombre = "Test", apellido = "User", rut = "1-9", correo = "a@a.cl",
            telefono = 1, contrasenia = "123", region = "RM", comuna = "Santiago"
        )

        // Y una lista de ofertas desordenada
        // Oferta 1: Misma Comuna (Prioridad 1)
        val ofertaComuna = OfertaLaboral(
            id = 1, titulo = "Oferta Comuna", empresa = "", descripcion = "", salario = "",
            region = "RM", comuna = "Santiago",
            fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(System.currentTimeMillis() + 100000), // Futuro
            cupos = 1, idEmpleador = 2
        )

        // Oferta 2: Misma Región, distinta comuna (Prioridad 2)
        val ofertaRegion = OfertaLaboral(
            id = 2, titulo = "Oferta Region", empresa = "", descripcion = "", salario = "",
            region = "RM", comuna = "Providencia",
            fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(System.currentTimeMillis() + 100000),
            cupos = 1, idEmpleador = 2
        )

        // Oferta 3: Otra Región (Prioridad 3)
        val ofertaLejana = OfertaLaboral(
            id = 3, titulo = "Oferta Lejana", empresa = "", descripcion = "", salario = "",
            region = "Valparaíso", comuna = "Viña",
            fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(System.currentTimeMillis() + 100000),
            cupos = 1, idEmpleador = 2
        )

        // Oferta 4: Expirada (No debe aparecer)
        val ofertaExpirada = OfertaLaboral(
            id = 4, titulo = "Oferta Expirada", empresa = "", descripcion = "", salario = "",
            region = "RM", comuna = "Santiago",
            fechaPublicacion = Date(), fechaInicioContrato = Date(), 
            fechaTerminoContrato = Date(System.currentTimeMillis() - 100000), // Pasado
            cupos = 1, idEmpleador = 2
        )

        val listaDesordenada = listOf(ofertaLejana, ofertaExpirada, ofertaComuna, ofertaRegion)

        // WHEN: Ejecutamos el sorter
        val resultado = OfertaSorter.filtrarYOrdenarOfertas(listaDesordenada, usuario)

        // THEN:
        // 1. La oferta expirada no debe estar
        assertEquals("Debería haber solo 3 ofertas vigentes", 3, resultado.size)

        // 2. El orden debe ser: Comuna -> Region -> Lejana
        assertEquals("Primera debe ser la de misma comuna", "Oferta Comuna", resultado[0].titulo)
        assertEquals("Segunda debe ser la de misma región", "Oferta Region", resultado[1].titulo)
        assertEquals("Tercera debe ser la lejana", "Oferta Lejana", resultado[2].titulo)
    }
}