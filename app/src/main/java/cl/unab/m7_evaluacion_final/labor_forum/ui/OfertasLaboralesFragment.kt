package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.m7_evaluacion_final.databinding.FragmentOfertasLaboralesBinding
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.ui.adaptador.OfertaLaboralAdapter
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.OfertaLaboralViewModel

class OfertasLaboralesFragment : Fragment() {

    private var _binding: FragmentOfertasLaboralesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OfertaLaboralViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfertasLaboralesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        // 1. Inicializamos el Adapter
        val adapter = OfertaLaboralAdapter { ofertaSeleccionada: OfertaLaboral ->

            Toast.makeText(
                requireContext(),
                "Seleccionaste: ${ofertaSeleccionada.titulo}",
                Toast.LENGTH_SHORT
            ).show()

            val bundle = Bundle().apply {
                putInt("idOfertaLaboral", ofertaSeleccionada.id)
            }

            // Error con SafeArgs, corregir en el futuro
            // Navegamos usando el ID de la acción y el bundle
            // R.id.action_ofertasLaboralesFragment_to_detalleOfertaLaboralFragment debe existir si tu XML es correcto
            findNavController().navigate(
                cl.unab.m7_evaluacion_final.R.id.action_ofertasLaboralesFragment_to_detalleOfertaLaboralFragment,
                bundle
            )
        }

        // 2. Configuramos el RecyclerView
        binding.rvOfertasLaborales.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOfertasLaborales.adapter = adapter

        // 3. Observamos los datos del ViewModel
        viewModel.listaOfertas.observe(viewLifecycleOwner) { listaDeOfertas ->
            // Cuando la lista cambia (o se carga al inicio), actualizamos el adapter
            // Si la lista está vacía, podrías mostrar un texto de "No hay ofertas"
            adapter.submitList(listaDeOfertas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
