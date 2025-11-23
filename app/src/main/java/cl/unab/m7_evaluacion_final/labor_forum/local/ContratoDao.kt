package cl.unab.m7_evaluacion_final.labor_forum.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Contrato
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ContratoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarContrato(contrato: Contrato)

    // Para la vista "Contratos Activos" (Trabajador viendo sus trabajos)
    // Hacemos un JOIN para traer los datos de la oferta basándonos en el contrato
    @Query(
        """
        SELECT OfertaLaboral.* FROM OfertaLaboral
        INNER JOIN Contrato ON OfertaLaboral.id = Contrato.idOferta
        WHERE Contrato.idTrabajador = :idTrabajador AND Contrato.estado = 'ACTIVO'
    """
    )
    fun obtenerMisContratosActivos(idTrabajador: Int): Flow<List<OfertaLaboral>>

    // Validar si el usuario ya postuló a esta oferta (para deshabilitar el botón postular)
    @Query("SELECT COUNT(*) FROM Contrato WHERE idTrabajador = :idTrabajador AND idOferta = :idOferta")
    suspend fun existeContrato(idTrabajador: Int, idOferta: Int): Int

    // Verifica si existe algún contrato activo que choque con las fechas dadas
    @Query(
        """
        SELECT COUNT(*) FROM Contrato 
        WHERE idTrabajador = :idTrabajador 
        AND estado = 'ACTIVO' 
        AND fechaInicio <= :fechaFinNueva
        AND fechaTermino >= :fechaInicioNueva
    """
    )
    suspend fun verificarTopeHorario(
        idTrabajador: Int,
        fechaInicioNueva: Date,
        fechaFinNueva: Date
    ): Int

}