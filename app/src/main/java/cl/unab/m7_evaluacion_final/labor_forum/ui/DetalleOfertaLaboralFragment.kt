package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cl.unab.m7_evaluacion_final.databinding.FragmentDetalleOfertaLaboralBinding
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.OfertaLaboralViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class DetalleOfertaLaboralFragment : Fragment() {

    private var _binding: FragmentDetalleOfertaLaboralBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleOfertaLaboralFragmentArgs by navArgs()
    private val viewModel: OfertaLaboralViewModel by viewModels()

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

        binding.btnCancelar.setOnClickListener {
            findNavController().popBackStack()
        }

        // 4. Pedimos al ViewModel que busque la oferta y observamos los cambios
        viewModel.obtenerOferta(idOferta).observe(viewLifecycleOwner) { oferta ->
            if (oferta != null) {
                // 5. Llenamos la UI con los datos de la oferta
                // Asegúrate de que estos IDs (tvTituloDetalle, etc.) coincidan con tu XML 'fragment_detalle_oferta_laboral.xml'

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