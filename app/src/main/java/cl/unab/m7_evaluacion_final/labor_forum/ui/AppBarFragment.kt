package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cl.unab.m7_evaluacion_final.R
import cl.unab.m7_evaluacion_final.databinding.FragmentAppBarBinding

class AppBarFragment : Fragment() {

    private var _binding: FragmentAppBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Obtenemos el NavController del contenedor central
        val navController = requireActivity().supportFragmentManager
            .findFragmentById(R.id.fcCenterContainer)
            ?.findNavController()

        // 2. NUEVO: Escuchamos los cambios de pantalla para actualizar el título
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            // 'destination.label' contiene el texto android:label definido en nav_graph_main.xml
            // Si el label es null (ej: no definido), dejamos el título anterior o uno por defecto
            destination.label?.let { label ->
                binding.topAppBar.title = label
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            // Buscamos el NavController del contenedor central (donde queremos que cambie la pantalla)
            val navController = requireActivity().supportFragmentManager
                .findFragmentById(R.id.fcCenterContainer)
                ?.findNavController()

            when (menuItem.itemId) {
                R.id.menu_crear_oferta -> {
                    // Navegamos al fragmento de crear oferta
                    // Asegúrate de haber agregado este ID al nav_graph_main.xml
                    navController?.navigate(R.id.crearOfertaFragment)
                    true
                }

                R.id.menu_perfil -> {
                    navController?.navigate(R.id.perfilTrabajadorFragment)
                    true
                }

                R.id.menu_mis_ofertas -> {
                    // Navegamos al fragmento de "Mis Ofertas"
                    navController?.navigate(R.id.misOfertasFragment)
                    true
                }

                R.id.menu_registro_trabajos -> {
                    navController?.navigate(R.id.contratosActivosFragment)
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}