package cl.unab.m7_evaluacion_final.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.unab.m7_evaluacion_final.labor_forum.local.ContratoDao
import cl.unab.m7_evaluacion_final.labor_forum.local.OfertaLaboralDao
import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDao
import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDatabase
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Contrato
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

@RunWith(AndroidJUnit4::class)
class BaseDeDatosTest {

    private lateinit var db: UsuarioDatabase
    private lateinit var contratoDao: ContratoDao
    private lateinit var usuarioDao: UsuarioDao
    private lateinit var ofertaDao: OfertaLaboralDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UsuarioDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        
        contratoDao = db.contratoDao()
        usuarioDao = db.usuarioDao()
        ofertaDao = db.ofertaLaboralDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun obtenerMisContratosActivos_retornaSoloActivos() = runBlocking {
        // 1. Crear Usuario (Trabajador)
        val trabajador = Usuario(id = 1, nombre = "Pepe", apellido = "User", rut = "1-9", contrasenia = "123", region = "RM", comuna = "Stgo", correo = "test@test.com", telefono = 12345678)
        usuarioDao.insertarUsuario(trabajador)

        // 2. Crear Ofertas (CORREGIDO: idEmpleador debe existir, usamos el mismo usuario '1' para simplificar, o creamos otro)
        // En la realidad el empleador seria otro, pero para efectos de FK de OfertaLaboral, debe existir un usuario.
        // Usaremos el mismo id '1' para que pase la FK, aunque conceptualmente sea raro que el trabajador sea su propio empleador, SQL lo permite.
        
        val ofertaActiva = OfertaLaboral(id = 10, titulo = "Oferta Activa", empresa = "Empresa A", descripcion = "D", salario = "100", region = "R", comuna = "C", fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(), cupos = 1, idEmpleador = 1)
        ofertaDao.insertarOferta(ofertaActiva)
        
        val ofertaFinalizada = OfertaLaboral(id = 20, titulo = "Oferta Finalizada", empresa = "Empresa B", descripcion = "D", salario = "100", region = "R", comuna = "C", fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(), cupos = 1, idEmpleador = 1)
        ofertaDao.insertarOferta(ofertaFinalizada)
        
        // 3. Crear Contratos
        val contratoActivo = Contrato(id = 1, idOferta = 10, idTrabajador = 1, estado = "ACTIVO", fechaInicio = Date(), fechaTermino = Date())
        contratoDao.insertarContrato(contratoActivo)
        
        val contratoFinalizado = Contrato(id = 2, idOferta = 20, idTrabajador = 1, estado = "FINALIZADO", fechaInicio = Date(), fechaTermino = Date())
        contratoDao.insertarContrato(contratoFinalizado)

        // 4. EJECUTAR QUERY
        val listaContratos = contratoDao.obtenerMisContratosActivos(1).first()

        // 5. VALIDAR
        assertEquals("Debería traer solo 1 contrato (el activo)", 1, listaContratos.size)
        assertEquals("El título debe corresponder a la oferta activa", "Oferta Activa", listaContratos[0].titulo)
    }

    @Test
    fun obtenerOfertasExcluyendoUsuario_filtraMisPropiasOfertas() = runBlocking {
        // 1. Crear Usuario YO (ID 100)
        val yo = Usuario(id = 100, nombre = "Yo", apellido = "Mismo", rut = "1-1", contrasenia = "123", region = "RM", comuna = "Stgo", correo = "yo@test.com", telefono = 98765432)
        usuarioDao.insertarUsuario(yo)

        // 2. Crear Usuario OTRO (ID 200) -> NECESARIO PARA FK
        val otro = Usuario(id = 200, nombre = "Otro", apellido = "Sujeto", rut = "2-2", contrasenia = "123", region = "RM", comuna = "Stgo", correo = "otro@test.com", telefono = 11111111)
        usuarioDao.insertarUsuario(otro)
        
        // 3. Crear Ofertas
        val miOferta = OfertaLaboral(id = 1, titulo = "Mi Oferta", empresa = "Mi Empresa", descripcion = "...", salario = "1", region = "R", comuna = "C", fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(), cupos = 1, idEmpleador = 100)
        ofertaDao.insertarOferta(miOferta)

        val otraOferta = OfertaLaboral(id = 2, titulo = "Otra Oferta", empresa = "Otra Empresa", descripcion = "...", salario = "1", region = "R", comuna = "C", fechaPublicacion = Date(), fechaInicioContrato = Date(), fechaTerminoContrato = Date(), cupos = 1, idEmpleador = 200)
        ofertaDao.insertarOferta(otraOferta)

        // 4. EJECUTAR QUERY (Excluyendo ID 100)
        val listaOfertas = ofertaDao.obtenerOfertasExcluyendoUsuario(100).first()

        // 5. VALIDAR
        assertEquals("Debería haber solo 1 oferta en la lista", 1, listaOfertas.size)
        assertEquals("La oferta visible debe ser la 'Otra Oferta'", "Otra Oferta", listaOfertas[0].titulo)
    }
}