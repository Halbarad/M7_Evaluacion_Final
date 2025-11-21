package cl.unab.m7_evaluacion_final.labor_forum.auxiliar

import android.content.Context

object UserSession {
    private const val PREF_NAME = "user_session"
    private const val KEY_USER_ID = "key_user_id"

    fun guardarSesion(context: Context, userId: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(KEY_USER_ID, userId).apply()
    }

    fun obtenerUsuarioId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_USER_ID, -1) // -1 significa que no hay usuario logueado
    }

    fun cerrarSesion(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}