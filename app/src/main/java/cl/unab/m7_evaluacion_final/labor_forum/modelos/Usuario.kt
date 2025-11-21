package cl.unab.m7_evaluacion_final.labor_forum.modelos

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
indices = [Index(value = ["rut"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val rut: String,
    val correo: String,
    val telefono: Int,
    val contrasenia: String, // A futuro Hashear la contraseña
    val region: String,
    val comuna: String
)