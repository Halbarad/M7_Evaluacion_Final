package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cl.unab.m7_evaluacion_final.databinding.FragmentDetalleOfertaLaboralBinding
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.ContratoViewModel
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.OfertaLaboralViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class DetalleOfertaLaboralFragment : Fragment() {

    private var _binding: FragmentDetalleOfertaLaboralBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleOfertaLaboralFragmentArgs by navArgs()
    private val viewModel: OfertaLaboralViewModel by viewModels()
    private val contratoViewModel: ContratoViewModel by viewModels()
    private var ofertaActual: OfertaLaboral? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleOfertaLaboralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idOferta = args.idOfertaLaboral
        val ocultarBoton = args.ocultarBotonPostular

        // LÓGICA PARA OCULTAR EL BOTÓN
        if (ocultarBoton) {
            binding.btnPostular.visibility = View.GONE
        }

        binding.btnCancelar.setOnClickListener {
            findNavController().popBackStack()
        }

        // Configurar botón Postular
        binding.btnPostular.setOnClickListener {
            if (ofertaActual != null) {
                contratoViewModel.postularAOferta(ofertaActual!!)
            }
        }

        // Observadores para la postulación
        contratoViewModel.operacionExitosa.observe(viewLifecycleOwner) { exito ->
            if (exito) {
                Toast.makeText(requireContext(), "¡Postulación exitosa!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Volver atrás
            }
        }

        contratoViewModel.conflictoHorario.observe(viewLifecycleOwner) { hayConflicto ->
            if (hayConflicto) {Toast.makeText(
                requireContext(),
                "No puedes postular: Tienes otro trabajo activo en estas fechas.",
                Toast.LENGTH_LONG
            ).show()
            }
        }

        contratoViewModel.yaPostulado.observe(viewLifecycleOwner) { yaExiste ->
            if (yaExiste) {
                Toast.makeText(requireContext(), "Ya has postulado a esta oferta anteriormente", Toast.LENGTH_SHORT).show()
                binding.btnPostular.isEnabled = false
                binding.btnPostular.text = "Ya postulado"
            }
        }

        // 4. Pedimos al ViewModel que busque la oferta y observamos los cambios
        viewModel.obtenerOferta(idOferta).observe(viewLifecycleOwner) { oferta ->
            if (oferta != null) {
                ofertaActual = oferta // Guardamos la oferta para usarla en el botón

                // 5. Llenamos la UI con los datos de la oferta
                binding.tvTituloOferta.text = oferta.titulo
                binding.tvNombreEmpresa.text = oferta.empresa
                binding.tvDescripcionOferta.text = oferta.descripcion
                binding.tvUbicacion.text = "${oferta.comuna}, ${oferta.region}"

                // Formato de Salario
                val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.getDefault())
                formatoMoneda.maximumFractionDigits = 0
                binding.tvSalario.text = try {
                    formatoMoneda.format(oferta.salario.toLong())
                } catch (e: Exception) {
                    "${oferta.salario}"
                }

                binding.tvCupos.text = "Cupos disponibles: ${oferta.cupos}"

                // Formato de Fechas
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val inicio = formatoFecha.format(oferta.fechaInicioContrato)
                val fin = formatoFecha.format(oferta.fechaTerminoContrato)

                binding.tvFecha.text = "Duración: $inicio - $fin"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}