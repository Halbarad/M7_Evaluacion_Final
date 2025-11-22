package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cl.unab.m7_evaluacion_final.databinding.FragmentPerfilTrabajadorBinding
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.RegistroViewModel

class PerfilTrabajadorFragment : Fragment() {

    private var _binding: FragmentPerfilTrabajadorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilTrabajadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarDatosUsuario()
        configurarBotonLogout()
    }

    private fun cargarDatosUsuario() {
        // Observamos al usuario logueado
        viewModel.obtenerUsuarioLogueado().observe(viewLifecycleOwner) { usuario ->
            if (usuario != null) {
                // Asegúrate de tener estos IDs en tu XML o cámbialos por los que tengas
                // Ejemplo: binding.tvNombrePerfil.text = ...

                // Como no conozco los IDs exactos de tu XML de perfil,
                // aquí te dejo un ejemplo genérico. Descomenta y ajusta según tu diseño:

                binding.tvNombre.text = usuario.nombre
                binding.tvApellido.text = usuario.apellido
                binding.tvRut.text = usuario.rut
                binding.tvCorreo.text = usuario.correo
                binding.tvTelefono.text = "+56 9 ${usuario.telefono}"
                binding.tvUbicacion.text = "${usuario.comuna}, ${usuario.region}"
            }
        }
    }

    private fun configurarBotonLogout() {
        binding.btnLogout.setOnClickListener {
            // 1. Borramos la sesión guardada
            cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession.cerrarSesion(requireContext())

            // 2. Volvemos a la MainActivity (Login)
            val intent = android.content.Intent(requireContext(), cl.unab.m7_evaluacion_final.MainActivity::class.java)
            // Limpiamos el stack para que no puedan volver atrás
            intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // 3. Cerramos la Activity actual
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}