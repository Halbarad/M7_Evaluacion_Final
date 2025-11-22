package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ui.setupWithNavController
import cl.unab.m7_evaluacion_final.databinding.FragmentDockedToolbarBinding
import androidx.navigation.fragment.findNavController

class DockedToolbarFragment : Fragment() {

    private var _binding: FragmentDockedToolbarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDockedToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //1. Buscamos el NavController del contenedor CENTRAL
        val navController = requireActivity().supportFragmentManager
            .findFragmentById(cl.unab.m7_evaluacion_final.R.id.fcCenterContainer)
            ?.findNavController()

        if (navController != null) {
            // A. Vinculación visual (para que se ilumine el ícono correcto al navegar por otros medios)
            // IMPORTANTE: setupWithNavController a veces sobrescribe el listener, por eso lo llamamos primero.
            androidx.navigation.ui.NavigationUI.setupWithNavController(binding.bottomAppBar, navController)

            // B. Escuchamos cambios de destino para mantener sincronizado el menú inferior
            // Esto arregla el problema: Si navegas desde el AppBar, el BottomNav se entera y actualiza su selección.
            navController.addOnDestinationChangedListener { _, destination, _ ->
                // Intentamos encontrar el ítem de menú que coincide con el destino actual
                val menu = binding.bottomAppBar.menu
                for (i in 0 until menu.size()) {
                    val item = menu.getItem(i)
                    if (item.itemId == destination.id) {
                        item.isChecked = true
                        break
                    }
                }
            }

            // C. Listener Manual para garantizar la navegación al hacer clic abajo
            binding.bottomAppBar.setOnItemSelectedListener { item ->
                if (item.itemId != navController.currentDestination?.id) {
                    // Usamos navigate directamente con opciones para limpiar el backstack si es necesario
                    // O usamos la utilidad estándar que maneja bien el backstack
                    androidx.navigation.ui.NavigationUI.onNavDestinationSelected(item, navController)
                }
                true
            }
        }
    }

}