package cl.unab.m7_evaluacion_final.labor_forum.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral

@Dao
interface OfertaLaboralDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarOferta(oferta: OfertaLaboral)

    // Usamos Flow para recibir actualizaciones automáticas en tiempo real si cambian los datos
    @Query("SELECT * FROM OfertaLaboral ORDER BY fechaPublicacion DESC")
    fun obtenerTodasLasOfertas(): kotlinx.coroutines.flow.Flow<List<OfertaLaboral>>

    @Query("SELECT * FROM OfertaLaboral WHERE id = :id")
    fun obtenerOfertaPorId(id: Int): kotlinx.coroutines.flow.Flow<OfertaLaboral>

    @Query("""
        SELECT * FROM OfertaLaboral 
        WHERE idEmpleador != :idUsuarioExcluir 
        AND id NOT IN (SELECT idOferta FROM Contrato WHERE idTrabajador = :idUsuarioExcluir)
    """)
    fun obtenerOfertasExcluyendoUsuario(idUsuarioExcluir: Int): kotlinx.coroutines.flow.Flow<List<OfertaLaboral>>

    @Query("SELECT * FROM OfertaLaboral WHERE idEmpleador = :idEmpleador ORDER BY fechaTerminoContrato DESC")
    fun obtenerMisOfertas(idEmpleador: Int): kotlinx.coroutines.flow.Flow<List<OfertaLaboral>>
}