package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m7_evaluacion_final.databinding.FragmentRegistroTrabajadorBinding
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.RegistroViewModel
import android.text.Editable
import android.text.TextWatcher
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import cl.unab.m7_evaluacion_final.labor_forum.auxiliar.DatosGeograficos

class RegistroTrabajadorFragment : Fragment() {

    private var _binding: FragmentRegistroTrabajadorBinding? = null
    private val binding get() = _binding!!
    // Instancia del ViewModel
    private val viewModel: RegistroViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroTrabajadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarListasDesplegables()
        configurarFormatoRut()
        configurarBotonRegistro()
    }

    private fun configurarListasDesplegables() {
        // 1. Lista de Regiones usando el objeto auxiliar
        val adapterRegiones = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, DatosGeograficos.regiones)

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
            val adapterComunas = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listaComunas)
            autoCompleteComuna?.setAdapter(adapterComunas)
        }
    }

    private fun configurarBotonRegistro() {
        binding.btnRegistrarse.setOnClickListener {
            // 1. Capturar valores
            val nombre = binding.tilNombre.editText?.text.toString()
            val apellido = binding.tilApellido.editText?.text.toString()
            val rut = binding.tilRut.editText?.text.toString()
            val correo = binding.tilCorreo.editText?.text.toString()
            val telefonoStr = binding.tilTelFono.editText?.text.toString() // Ojo con el ID 'tilTelFono'
            val contrasenia = binding.tilContrasenia.editText?.text.toString() // Asumiendo que agregaste este campo
            val region = binding.tilRegion.editText?.text.toString()
            val comuna = binding.tilComuna.editText?.text.toString()

            // 2. Validaciones básicas (Opcional pero recomendado)
            if (nombre.isEmpty() || apellido.isEmpty() || rut.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || region.isEmpty() || comuna.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor complete los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Conversión segura de teléfono a Int
            val telefono = telefonoStr.toIntOrNull() ?: 0

            // 3. Llamar al ViewModel para guardar
            viewModel.registrarUsuario(
                nombre, apellido, rut, correo, telefono, region, comuna, contrasenia
            )

            Toast.makeText(requireContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()

            val action = RegistroTrabajadorFragmentDirections.actionRegistroFragmentToInicioFragment()
            findNavController().navigate(action)
        }
    }

    private fun configurarFormatoRut() {
        binding.tilRut.editText?.addTextChangedListener(object : TextWatcher {
            // Flag para evitar bucles infinitos (porque al formatear cambiamos el texto y eso llama al listener de nuevo)
            var editando = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (editando) return

                editando = true
                try {
                    // 1. Limpiamos el input (sacamos puntos, guiones y cualquier cosa que no sea número o K)
                    val rutLimpio = s.toString().replace(Regex("[^0-9kK]"), "")

                    // 2. Solo formateamos si hay más de 1 caracter (cuerpo + dv)
                    if (rutLimpio.length > 1) {
                        val dv = rutLimpio.last() // El último caracter es el verificador
                        val cuerpo = rutLimpio.substring(0, rutLimpio.length - 1) // El resto es el cuerpo

                        // 3. Formateamos el cuerpo con puntos (usando formato chileno)
                        val symbols = DecimalFormatSymbols(Locale("es", "CL"))
                        symbols.groupingSeparator = '.'
                        val decimalFormat = DecimalFormat("###,###", symbols)

                        // Parseamos a Long para que DecimalFormat ponga los puntos
                        val cuerpoFormateado = try {
                            decimalFormat.format(cuerpo.toLong())
                        } catch (e: NumberFormatException) {
                            cuerpo // Si falla (ej: es muy largo), devolvemos sin formato
                        }

                        // 4. Armamos el RUT final
                        val rutFinal = "$cuerpoFormateado-$dv"

                        // 5. Seteamos el texto en el campo
                        binding.tilRut.editText?.setText(rutFinal)

                        // 6. Movemos el cursor al final para que el usuario siga escribiendo cómodo
                        binding.tilRut.editText?.setSelection(rutFinal.length)
                    }
                } catch (e: Exception) {
                    // Si algo falla, no hacemos nada y dejamos que el usuario siga escribiendo
                } finally {
                    editando = false
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Importante: Limpiamos el binding para liberar memoria cuando la vista se destruye.
        _binding = null
    }

}