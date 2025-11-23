package cl.unab.m7_evaluacion_final.labor_forum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession
import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDatabase
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.repositorio.OfertaLaboralRepositorio
import cl.unab.m7_evaluacion_final.labor_forum.repositorio.RegistroRepositorio
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date

class OfertaLaboralViewModel(application: Application) : AndroidViewModel(application) {

    private val database = UsuarioDatabase.getDatabase(application)
    private val repositorioOfertas = OfertaLaboralRepositorio(database.ofertaLaboralDao())
    private val repositorioUsuarios = RegistroRepositorio(database.usuarioDao())

    // Esta variable ahora contiene toda la lógica de filtrado y ordenamiento
    val listaOfertas: LiveData<List<OfertaLaboral>>

    init {
        // 1. Obtenemos el ID del usuario logueado
        val context = getApplication<Application>().applicationContext
        val idUsuarioLogueado = UserSession.obtenerUsuarioId(context)

        // 2. Flujo del Usuario Actual (para saber su región y comuna)
        val flujoUsuario = repositorioUsuarios.obtenerUsuarioPorId(idUsuarioLogueado)

        // 3. Flujo de Ofertas (Excluyendo las propias)
        val flujoOfertas = repositorioOfertas.obtenerOfertasExcluyendoUsuario(idUsuarioLogueado)

        // 4. Combinamos ambos flujos y aplicamos la lógica
        val flujoCombinado = combine(flujoUsuario, flujoOfertas) { usuario, ofertas ->
            if (usuario == null) return@combine emptyList<OfertaLaboral>()

            ofertas.filter { oferta ->
                // FILTRO: No mostrar si fechaExpiracion >= fechaInicioContrato
                // Es decir, solo mostramos si expiracion es MENOR a inicio
                oferta.fechaTerminoContrato.time >= Date().time
            }.sortedWith(
                compareBy<OfertaLaboral> { oferta ->
                    // ORDENAMIENTO PRIORITARIO:
                    // Retornamos un valor numérico para agrupar (menor valor va primero)
                    when {
                        oferta.comuna == usuario.comuna -> 1 // Prioridad 1: Misma Comuna
                        oferta.region == usuario.region -> 2 // Prioridad 2: Misma Región
                        else -> 3                            // Prioridad 3: El resto
                    }
                }.thenBy { oferta ->
                    // ORDENAMIENTO SECUNDARIO:
                    // Fecha inicio contrato: antiguas primero (Ascendente)
                    oferta.fechaInicioContrato
                }
            )
        }

        // Convertimos el flujo resultante a LiveData para la vista
        listaOfertas = flujoCombinado.asLiveData()
    }

    // ... (Tu función crearOferta se mantiene igual) ...
    fun crearOferta(
        titulo: String, empresa: String, descripcion: String, salario: String,
        region: String, comuna: String, fechaInicio: Date, fechaTermino: Date, cupos: Int, idEmpleador: Int
    ) {
        viewModelScope.launch {
            val fechaPublicacion = Date()

            val nuevaOferta = OfertaLaboral(
                titulo = titulo, empresa = empresa, descripcion = descripcion, salario = salario,
                region = region, comuna = comuna, fechaPublicacion = fechaPublicacion,
                fechaInicioContrato = fechaInicio, fechaTerminoContrato = fechaTermino,
                cupos = cupos, idEmpleador = idEmpleador
            )
            repositorioOfertas.insertarOferta(nuevaOferta)
        }
    }

    fun actualizarOferta(
        id: Int, titulo: String, descripcion: String, salario: String,
        region: String, comuna: String, fechaInicio: Date, fechaTermino: Date, cupos: Int
    ) {
        viewModelScope.launch {
            // Recuperamos primero la oferta actual para mantener los datos que no cambian
            // Nota: Esto es un atajo, idealmente deberíamos hacer una consulta primero
            // Pero como es una actualización, el repositorio debería manejarlo.
            // Aquí asumiremos que tenemos que recuperar el objeto completo.

            // Como estamos dentro de una corrutina, podemos recolectar el flujo
            repositorioOfertas.obtenerOfertaPorId(id).collect { ofertaActual ->
                if (ofertaActual != null) {
                    val ofertaActualizada = ofertaActual.copy(
                        titulo = titulo,
                        descripcion = descripcion,
                        salario = salario,
                        region = region,
                        comuna = comuna,
                        fechaInicioContrato = fechaInicio,
                        fechaTerminoContrato = fechaTermino,
                        cupos = cupos
                    )
                    repositorioOfertas.actualizarOferta(ofertaActualizada)
                }
                // Cancelamos la colección después de obtener el primer valor para evitar loops
                // En Flow esto se hace lanzando una excepción de cancelación o usando first()
                throw kotlinx.coroutines.CancellationException("Oferta actualizada")
            }
        }
    }


    // Función para obtener detalle (se mantiene igual)
    fun obtenerOferta(id: Int): LiveData<OfertaLaboral> {
        return repositorioOfertas.obtenerOfertaPorId(id).asLiveData()
    }

    fun obtenerMisOfertas(): LiveData<List<OfertaLaboral>> {
        val context = getApplication<Application>().applicationContext
        // Obtenemos el ID del usuario logueado
        val idUsuario = UserSession.obtenerUsuarioId(context)

        // Retornamos el flujo convertido a LiveData
        return repositorioOfertas.obtenerMisOfertas(idUsuario).asLiveData()
    }
}