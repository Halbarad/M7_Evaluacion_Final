package cl.unab.m7_evaluacion_final.labor_forum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession
import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDatabase
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario
import cl.unab.m7_evaluacion_final.labor_forum.repositorio.RegistroRepositorio
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    // Inicializamos la BD y el Repositorio
    private val database = UsuarioDatabase.getDatabase(application)
    private val repositorio = RegistroRepositorio(database.usuarioDao())

    fun registrarUsuario(
        nombre: String,
        apellido: String,
        rut: String,
        correo: String,
        telefono: Int,
        region: String,
        comuna: String,
        contrasena: String
    ) {
        viewModelScope.launch {
            val nuevoUsuario = Usuario(
                nombre = nombre,
                apellido = apellido,
                rut = rut,
                correo = correo,
                telefono = telefono,
                contrasenia = contrasena,
                region = region,
                comuna = comuna
            )

            // Llamamos al repositorio en lugar del DAO directo
            repositorio.insertarUsuario(nuevoUsuario)
        }
    }

    // Esta función devuelve los datos del usuario logueado en tiempo real
    fun obtenerUsuarioLogueado(): LiveData<Usuario> {
        val context = getApplication<Application>().applicationContext
        // 1. Recuperamos el ID de la sesión
        val idUsuario = UserSession.obtenerUsuarioId(context)

        // 2. Retornamos el LiveData desde la BD
        return repositorio.obtenerUsuarioPorId(idUsuario).asLiveData()
    }

}