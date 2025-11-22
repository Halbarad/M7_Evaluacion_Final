package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import cl.unab.m7_evaluacion_final.databinding.FragmentCrearOfertaBinding
import cl.unab.m7_evaluacion_final.labor_forum.auxiliar.DatosGeograficos
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.OfertaLaboralViewModel
import java.text.NumberFormat

class CrearOfertaFragment : Fragment() {

    private var _binding: FragmentCrearOfertaBinding? = null
    private val binding get() = _binding!!
    private var fechaInicioSeleccionada: Date? = null
    private var fechaTerminoSeleccionada: Date? = null
    private val viewModel: OfertaLaboralViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearOfertaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarListasDesplegables()
        configurarSelectorFecha()
        configurarFormatoMoneda()
        configurarBotonCrear()
        configurarBotonCancelar()
    }

    private fun configurarListasDesplegables() {
        // 1. Usamos la lista centralizada de Regiones desde DatosGeograficos
        val adapterRegiones = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            DatosGeograficos.regiones
        )

        // Casteamos los inputs a AutoCompleteTextView
        val autoCompleteRegion = binding.tilRegion.editText as? AutoCompleteTextView
        val autoCompleteComuna = binding.tilComuna.editText as? AutoCompleteTextView

        autoCompleteRegion?.setAdapter(adapterRegiones)

        // 2. Listener: Cuando seleccionan una Región
        autoCompleteRegion?.setOnItemClickListener { parent, _, position, _ ->
            val regionSeleccionada = parent.getItemAtPosition(position) as String

            // A. Limpiamos la comuna anterior porque ya no es válida
            autoCompleteComuna?.setText("", false)

            // B. Obtenemos las comunas usando el objeto auxiliar
            val listaComunas = DatosGeograficos.obtenerComunasPorRegion(regionSeleccionada)

            // C. Creamos y asignamos el nuevo adaptador para las comunas
            val adapterComunas = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                listaComunas
            )
            autoCompleteComuna?.setAdapter(adapterComunas)
        }
    }

    private fun configurarSelectorFecha() {
        // El campo donde mostraremos la fecha
        val campoFecha = binding.tilEditDate

        // Listener al hacer clic en el campo
        campoFecha.setOnClickListener {
            // 1. Construimos el selector de Rango de Fechas
            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Selecciona el rango de fechas")
                .setSelection(
                    androidx.core.util.Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

            // 2. Manejamos la respuesta positiva (cuando el usuario da OK)
            datePicker.addOnPositiveButtonClickListener { selection ->
                // selection es un Pair<Long, Long> con fecha inicio y fin
                val fechaInicio = selection.first
                val fechaFin = selection.second

                fechaInicioSeleccionada = Date(fechaInicio)
                fechaTerminoSeleccionada = Date(fechaFin)

                // Formateamos las fechas para mostrarlas como texto
                val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // Es vital poner UTC para que no reste horas por la zona horaria y cambie el día
                formato.timeZone = TimeZone.getTimeZone("UTC")

                val inicioStr = formato.format(Date(fechaInicio))
                val finStr = formato.format(Date(fechaFin))

                // 3. Mostramos el resultado en el campo
                campoFecha.setText("$inicioStr - $finStr")
                if (inicioStr == finStr) {
                    campoFecha.setText(inicioStr)
                } else {
                    campoFecha.setText("$inicioStr - $finStr")
                }
            }

            // 3. Mostramos el diálogo usando el ChildFragmentManager
            datePicker.show(childFragmentManager, "DATE_PICKER")
        }
    }

    private fun configurarFormatoMoneda() {
    binding.tilSalario.editText?.addTextChangedListener(object : TextWatcher {
        private var editando = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (editando) return

            editando = true
            try {
                // 1. Limpiamos el input: eliminamos todo lo que no sea un número
                val stringLimpio = s.toString().replace(Regex("[^\\d]"), "")

                if (stringLimpio.isNotEmpty()) {
                    // 2. Convertimos a número (Long)
                    val precio = stringLimpio.toLong()

                    // 3. Formateamos a moneda chilena (es-CL)
                    // Esto agrega automáticamente el signo $ y los puntos de miles
                    val formato = NumberFormat.getCurrencyInstance(Locale.getDefault())
                    formato.maximumFractionDigits = 0 // Eliminamos decimales para CLP

                    val precioFormateado = formato.format(precio)

                    // 4. Seteamos el texto formateado
                    binding.tilSalario.editText?.setText(precioFormateado)

                    // 5. Movemos el cursor al final
                    binding.tilSalario.editText?.setSelection(precioFormateado.length)
                }
            } catch (e: NumberFormatException) {
                // Si el número es demasiado grande para un Long, no hacemos nada
            } finally {
                editando = false
            }
        }
    })
}

    private fun configurarBotonCrear() {
        binding.btnCrearOferta.setOnClickListener {
            val titulo = binding.tilTitulo.editText?.text.toString()
            val empresa = binding.tilEmpresa.editText?.text.toString()
            val descripcion = binding.tilDescripcion.editText?.text.toString()
            val salario = binding.tilSalario.editText?.text.toString()
            val region = binding.tilRegion.editText?.text.toString()
            val comuna = binding.tilComuna.editText?.text.toString()

            if (titulo.isEmpty() || empresa.isEmpty() || fechaInicioSeleccionada == null) {
                Toast.makeText(requireContext(), "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Obtener el ID real del usuario logueado.
            // Por ahora uso 1 como ejemplo. Debes implementar un UserSessionManager o similar.
            val idEmpleador = cl.unab.m7_evaluacion_final.labor_forum.auxiliar.UserSession.obtenerUsuarioId(requireContext())

            if (idEmpleador == -1) {
                Toast.makeText(requireContext(), "Error de sesión. Ingrese nuevamente.", Toast.LENGTH_SHORT).show()
                // Aquí podrías navegar de vuelta al Login si quisieras ser estricto
                return@setOnClickListener
            }

            viewModel.crearOferta(
                titulo, empresa, descripcion, salario, region, comuna,
                fechaInicioSeleccionada!!, fechaTerminoSeleccionada!!, idEmpleador
            )

            Toast.makeText(requireContext(), "Oferta creada con éxito", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack() // Volver atrás
        }
    }

    private fun configurarBotonCancelar() {
        binding.btnCancelar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}