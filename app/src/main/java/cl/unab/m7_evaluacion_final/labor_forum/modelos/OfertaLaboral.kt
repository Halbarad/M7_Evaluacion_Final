package cl.unab.m7_evaluacion_final.labor_forum.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["idEmpleador"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OfertaLaboral(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val empresa: String,
    val descripcion: String,
    val salario: String,
    val region: String,
    val comuna: String,
    val fechaPublicacion: Date,
    val fechaInicioContrato: Date,
    val fechaTerminoContrato: Date,
    val cupos: Int,
    val idEmpleador: Int
    )