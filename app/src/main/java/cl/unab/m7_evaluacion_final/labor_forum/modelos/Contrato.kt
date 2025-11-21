package cl.unab.m7_evaluacion_final.labor_forum.modelos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "contratos",
    foreignKeys = [
        ForeignKey(
            entity = OfertaLaboral::class,
            parentColumns = ["id"],
            childColumns = ["idOferta"],
            onDelete = ForeignKey.CASCADE // Si borran la oferta, se borra el contrato
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["idTrabajador"],
            onDelete = ForeignKey.CASCADE // Si borran al usuario, se borra el contrato
        )
    ],
    // Índices para hacer las consultas rápidas
    indices = [Index(value = ["idOferta"]), Index(value = ["idTrabajador"])]
)
data class Contrato(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOferta: Int,
    val idTrabajador: Int,
    // Aquí controlamos si el trabajador sigue trabajando o ya terminó
    // Valores posibles: "ACTIVO", "FINALIZADO"
    val estado: String = "ACTIVO",
    val fechaInicio: String,
    val fechaFin: String? = null
)
