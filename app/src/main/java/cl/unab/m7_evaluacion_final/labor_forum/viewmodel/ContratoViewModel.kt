package cl.unab.m7_evaluacion_final.labor_forum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession
import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDatabase
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Contrato
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.repositorio.ContratoRepositorio
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData


class ContratoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = UsuarioDatabase.getDatabase(application)
    private val repositorio = ContratoRepositorio(database.contratoDao())

    val operacionExitosa = MutableLiveData<Boolean>()
    val yaPostulado = MutableLiveData<Boolean>()
    val conflictoHorario = MutableLiveData<Boolean>()

    fun postularAOferta(oferta: OfertaLaboral) {
        val context = getApplication<Application>().applicationContext
        val idTrabajador = UserSession.obtenerUsuarioId(context)

        if (idTrabajador == -1) return // Error de sesión

        viewModelScope.launch {
            // 1. Verificar si ya existe postulación
            if (repositorio.existeContrato(idTrabajador, oferta.id)) {
                yaPostulado.postValue(true)
                return@launch
            }

            // 2. Crear el contrato
            val nuevoContrato = Contrato(
                idTrabajador = idTrabajador,
                idOferta = oferta.id,
                fechaInicio = oferta.fechaInicioContrato,
                fechaTermino = oferta.fechaTerminoContrato,
                estado = "ACTIVO"
            )

            // 3. Guardar
            repositorio.insertarContrato(nuevoContrato)
            operacionExitosa.postValue(true)
        }
    }

    fun obtenerMisContratosActivos(): LiveData<List<OfertaLaboral>> {
        val context = getApplication<Application>().applicationContext
        val idTrabajador = UserSession.obtenerUsuarioId(context)

        return repositorio.obtenerMisContratosActivos(idTrabajador).asLiveData()
    }
}