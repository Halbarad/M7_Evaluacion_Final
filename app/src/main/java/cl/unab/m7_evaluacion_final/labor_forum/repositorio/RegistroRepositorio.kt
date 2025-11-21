package cl.unab.m7_evaluacion_final.labor_forum.repositorio

import cl.unab.m7_evaluacion_final.labor_forum.local.UsuarioDao
import cl.unab.m7_evaluacion_final.labor_forum.modelos.Usuario

class RegistroRepositorio(private val usuarioDao: UsuarioDao) {

    // Función para insertar un usuario
    // 'suspend' indica que esta función se ejecuta dentro de una corutina
    suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertarUsuario(usuario)
    }

    // Opcional: Función para validar si existe un usuario (para login o validación de registro)
    suspend fun obtenerUsuarioPorRut(rut: String): Usuario? {
        return usuarioDao.obtenerUsuarioPorRut(rut)
    }

    suspend fun login(rut: String, pass: String): Usuario? {
        return usuarioDao.login(rut, pass)
    }

    fun obtenerUsuarioPorId(id: Int): kotlinx.coroutines.flow.Flow<Usuario> {
        return usuarioDao.obtenerUsuarioPorId(id)
    }
}