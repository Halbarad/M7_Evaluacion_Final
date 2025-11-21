package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.m7_evaluacion_final.databinding.FragmentMisOfertasBinding
import cl.unab.m7_evaluacion_final.labor_forum.ui.adaptador.MisOfertasAdapter
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.OfertaLaboralViewModel

class MisOfertasFragment : Fragment() {

    private var _binding: FragmentMisOfertasBinding? = null
    private val binding get() = _binding!!

    // Reutilizamos el mismo ViewModel
    private val viewModel: OfertaLaboralViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMisOfertasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        // 1. Inicializar Adapter
        val adapter = MisOfertasAdapter { oferta ->
            // Aquí puedes navegar al detalle o editar
            Toast.makeText(requireContext(), "Oferta: ${oferta.titulo}", Toast.LENGTH_SHORT).show()
        }

        // 2. Configurar RecyclerView
        binding.rvMisOfertas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMisOfertas.adapter = adapter

        // 3. Observar datos específicos de "Mis Ofertas"
        viewModel.obtenerMisOfertas().observe(viewLifecycleOwner) { lista ->
            if (lista.isEmpty()) {
                Toast.makeText(requireContext(), "No has creado ofertas aún", Toast.LENGTH_SHORT).show()
            }
            adapter.submitList(lista)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}