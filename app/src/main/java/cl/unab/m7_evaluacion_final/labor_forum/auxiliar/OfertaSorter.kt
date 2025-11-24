package cl.unab.m7_evaluacion_final.labor_forum.auxiliar

import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario
import java.util.Date

object OfertaSorter {

    fun filtrarYOrdenarOfertas(
        ofertas: List<OfertaLaboral>,
        usuario: Usuario
    ): List<OfertaLaboral> {
        return ofertas.filter { oferta ->
            // FILTRO: Solo mostramos si expiracion es MAYOR o IGUAL a hoy (vigentes)
            // Nota: Para simplificar el test, usamos la lógica de tiempo tal cual viene
            oferta.fechaTerminoContrato.time >= System.currentTimeMillis()
        }.sortedWith(
            compareBy<OfertaLaboral> { oferta ->
                // ORDENAMIENTO PRIORITARIO:
                // 1: Misma Comuna
                // 2: Misma Región
                // 3: El resto
                when {
                    oferta.comuna == usuario.comuna -> 1 
                    oferta.region == usuario.region -> 2 
                    else -> 3                            
                }
            }.thenBy { oferta ->
                // ORDENAMIENTO SECUNDARIO:
                // Fecha inicio contrato: antiguas primero (Ascendente)
                oferta.fechaInicioContrato
            }
        )
    }
}