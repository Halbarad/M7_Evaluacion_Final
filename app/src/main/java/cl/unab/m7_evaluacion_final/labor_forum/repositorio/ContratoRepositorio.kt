package cl.unab.m7_evaluacion_final.labor_forum.repositorio

import cl.unab.m7_evaluacion_final.labor_forum.local.ContratoDao
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Contrato

class ContratoRepositorio(private val dao: ContratoDao) {

    suspend fun insertarContrato(contrato: Contrato) {
        dao.insertarContrato(contrato)
    }

    suspend fun existeContrato(idTrabajador: Int, idOferta: Int): Boolean {
        return dao.existeContrato(idTrabajador, idOferta) > 0
    }

    fun obtenerMisContratosActivos(idTrabajador: Int): kotlinx.coroutines.flow.Flow<List<cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral>> {
        return dao.obtenerMisContratosActivos(idTrabajador)
    }

    suspend fun verificarTopeHorario(idTrabajador: Int, fechaInicio: java.util.Date, fechaFin: java.util.Date): Boolean {
        return dao.verificarTopeHorario(idTrabajador, fechaInicio, fechaFin) > 0
    }
}