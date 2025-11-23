package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.unab.m7_evaluacion_final.databinding.FragmentContratosActivosBinding
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.m7_evaluacion_final.labor_forum.ui.adaptador.OfertaLaboralAdapter
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.ContratoViewModel

class ContratosActivosFragment : Fragment() {

    private var _binding: FragmentContratosActivosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContratoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContratosActivosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Reutilizamos el adapter de ofertas laborales
        // Si quisieras mostrar un botón "Ver contrato" distinto, tendrías que crear otro adapter.
        val adapter = OfertaLaboralAdapter { oferta ->
            Toast.makeText(requireContext(), "Trabajo: ${oferta.titulo}", Toast.LENGTH_SHORT).show()
        }

        binding.rvContratos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContratos.adapter = adapter

        viewModel.obtenerMisContratosActivos().observe(viewLifecycleOwner) { lista ->
            if (lista.isEmpty()) {
                Toast.makeText(requireContext(), "No tienes trabajos activos", Toast.LENGTH_SHORT).show()
            }
            adapter.submitList(lista)
        }
    }

}