package cl.unab.m7_evaluacion_final.labor_forum.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Contrato

@Database(
    entities = [Usuario::class, OfertaLaboral::class, Contrato::class], // Agrega aquí las otras entidades: [Usuario::class, OfertaLaboral::class, Contrato::class]
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UsuarioDatabase : RoomDatabase() {

    // Debes declarar una función abstracta por cada DAO que tengas
    abstract fun usuarioDao(): UsuarioDao
    abstract fun ofertaLaboralDao(): OfertaLaboralDao
    abstract fun contratoDao(): ContratoDao

    companion object {
        @Volatile
        private var INSTANCE: UsuarioDatabase? = null

        fun getDatabase(context: Context): UsuarioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDatabase::class.java,
                    "usuario_database" // Nombre del archivo de la BD en el dispositivo
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}