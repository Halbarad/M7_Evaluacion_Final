package cl.unab.m7_evaluacion_final.labor_forum.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario

@Dao
interface UsuarioDao {

    // Función para registrar un nuevo usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)

    // Función auxiliar para validar si el usuario ya existe (útil para el login después)
    @Query("SELECT * FROM Usuario WHERE rut = :rut LIMIT 1")
    suspend fun obtenerUsuarioPorRut(rut: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE rut = :rut AND contrasenia = :pass LIMIT 1")
    suspend fun login(rut: String, pass: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun obtenerUsuarioPorId(id: Int): kotlinx.coroutines.flow.Flow<Usuario>
}