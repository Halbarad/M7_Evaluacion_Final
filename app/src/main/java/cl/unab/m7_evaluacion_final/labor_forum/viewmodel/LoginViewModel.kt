package cl.unab.m7_evaluacion_final.labor_forum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDatabase
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario
import cl.unab.m7_evaluacion_final.labor_forum.repositorio.RegistroRepositorio
import kotlinx.coroutines.launch
import cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val database = UsuarioDatabase.getDatabase(application)
    private val repositorio = RegistroRepositorio(database.usuarioDao())

    // LiveData para comunicar el resultado a la Vista (Fragment)
    // true = Login exitoso, false = Login fallido
    private val _loginExitoso = MutableLiveData<Boolean>()
    val loginExitoso: LiveData<Boolean> get() = _loginExitoso

    // LiveData para saber qué usuario se logueó (opcional, pero útil)
    private val _usuarioLogueado = MutableLiveData<Usuario?>()
    val usuarioLogueado: LiveData<Usuario?> get() = _usuarioLogueado

    fun validarCredenciales(rut: String, pass: String) {
        viewModelScope.launch {
            // Llamamos a la base de datos en segundo plano
            val usuario = repositorio.login(rut, pass)

            if (usuario != null) {
                cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession.guardarSesion(getApplication(), usuario.id)

                // Encontramos al usuario!
                _usuarioLogueado.value = usuario
                _loginExitoso.value = true
            } else {
                // Rut o contraseña incorrectos
                _loginExitoso.value = false
            }
        }
    }

    // Función para resetear el estado después de navegar
    fun resetLoginState() {
        _loginExitoso.value = false
    }
}