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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}