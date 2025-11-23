package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ui.setupWithNavController
import cl.unab.m7_evaluacion_final.databinding.FragmentDockedToolbarBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

class DockedToolbarFragment : Fragment() {

    private var _binding: FragmentDockedToolbarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDockedToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Obtener NavController
        val navController = requireActivity().supportFragmentManager
            .findFragmentById(cl.unab.m7_evaluacion_final.R.id.fcCenterContainer)
            ?.findNavController()

        // 2. Vinculación Automática y Única
        if (navController != null) {
            // Esto maneja clics, re-selecciones y coloreado automáticamente.
            // IMPORTANTE: Para que funcione, los IDs del menú DEBEN coincidir con los del nav_graph (que ya verificamos que sí).
            NavigationUI.setupWithNavController(binding.bottomAppBar, navController)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}