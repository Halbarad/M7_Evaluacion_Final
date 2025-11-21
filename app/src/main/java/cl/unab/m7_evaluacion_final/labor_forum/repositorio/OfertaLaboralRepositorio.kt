package cl.unab.m7_evaluacion_final.labor_forum.repositorio

import cl.unab.m7_evaluacion_final.labor_forum.local.OfertaLaboralDao
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral

class OfertaLaboralRepositorio(private val dao: OfertaLaboralDao) {
    suspend fun insertarOferta(oferta: OfertaLaboral) {
        dao.insertarOferta(oferta)
    }

    fun obtenerTodasLasOfertas(): kotlinx.coroutines.flow.Flow<List<OfertaLaboral>> {
        return dao.obtenerTodasLasOfertas()
    }

    fun obtenerOfertaPorId(id: Int): kotlinx.coroutines.flow.Flow<OfertaLaboral> {
        return dao.obtenerOfertaPorId(id)
    }

    fun obtenerOfertasExcluyendoUsuario(idUsuario: Int): kotlinx.coroutines.flow.Flow<List<OfertaLaboral>> {
        return dao.obtenerOfertasExcluyendoUsuario(idUsuario)
    }

    fun obtenerMisOfertas(idEmpleador: Int): kotlinx.coroutines.flow.Flow<List<OfertaLaboral>> {
        return dao.obtenerMisOfertas(idEmpleador)
    }
}