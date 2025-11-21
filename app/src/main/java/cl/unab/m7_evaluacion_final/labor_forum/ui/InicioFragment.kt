package cl.unab.m7_evaluacion_final.labor_forum.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m7_evaluacion_final.databinding.FragmentInicioBinding
import cl.unab.m7_evaluacion_final.labor_forum.viewmodel.LoginViewModel
import cl.unab.m7_evaluacion_final.labor_forum.ui.PrincipalActivity
import android.text.Editable
import android.text.TextWatcher
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    // Instancia del ViewModel de Login
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración de botones
        configurarBotonRegistrarme()
        configurarBotonIngresar()
        configurarFormatoRut()

        // Observadores de LiveData
        observarLogin()
    }

    private fun configurarFormatoRut() {
        binding.tilRut.editText?.addTextChangedListener(object : TextWatcher {
            var editando = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (editando) return

                editando = true
                try {
                    // 1. Limpiamos el input
                    val rutLimpio = s.toString().replace(Regex("[^0-9kK]"), "")

                    // 2. Solo formateamos si hay cuerpo + dv
                    if (rutLimpio.length > 1) {
                        val dv = rutLimpio.last()
                        val cuerpo = rutLimpio.substring(0, rutLimpio.length - 1)

                        // 3. Formato con puntos
                        val symbols = DecimalFormatSymbols(Locale.getDefault())
                        symbols.groupingSeparator = '.'
                        val decimalFormat = DecimalFormat("###,###", symbols)

                        val cuerpoFormateado = try {
                            decimalFormat.format(cuerpo.toLong())
                        } catch (e: NumberFormatException) {
                            cuerpo
                        }

                        // 4. Armamos el RUT final
                        val rutFinal = "$cuerpoFormateado-$dv"

                        // 5. Seteamos y movemos cursor
                        binding.tilRut.editText?.setText(rutFinal)
                        binding.tilRut.editText?.setSelection(rutFinal.length)
                    }
                } catch (e: Exception) {
                    // Ignorar errores de formato
                } finally {
                    editando = false
                }
            }
        })
    }

    private fun configurarBotonRegistrarme() {
        binding.btnRegistrarme.setOnClickListener {
            // Navegación hacia el fragmento de registro (SafeArgs)
            val action = InicioFragmentDirections.actionInicioFragmentToRegistroFragment()
            findNavController().navigate(action)
        }
    }

    private fun configurarBotonIngresar() {
        binding.btnIngresar.setOnClickListener {
            val rut = binding.tilRut.editText?.text.toString()
            // Asegúrate de usar el ID correcto de tu layout para la contraseña (tilContrasenia, tilContraseña, etc.)
            val pass = binding.tilContrasenia.editText?.text.toString()

            if (rut.isNotEmpty() && pass.isNotEmpty()) {
                // Solicitamos al ViewModel que verifique las credenciales
                loginViewModel.validarCredenciales(rut, pass)
            } else {
                Toast.makeText(requireContext(), "Por favor ingrese RUT y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observarLogin() {
        loginViewModel.loginExitoso.observe(viewLifecycleOwner) { loginExitoso ->
            if (loginExitoso) {
                Toast.makeText(requireContext(), "¡Bienvenido!", Toast.LENGTH_SHORT).show()

                // --- CAMBIO CLAVE: Navegación entre Activities ---

                // 1. Creamos el Intent hacia PrincipalActivity
                val intent = Intent(requireActivity(), PrincipalActivity::class.java)

                // 2. Iniciamos la nueva Activity
                startActivity(intent)

                // 3. (Opcional) Finalizamos la MainActivity para que el usuario no pueda volver al login con "Atrás"
                requireActivity().finish()

                // Reseteamos el estado del ViewModel (buena práctica)
                loginViewModel.resetLoginState()
            } else {
                // Solo mostramos el error si hubo un intento activo de login.
                // (Podrías agregar un estado 'isLoading' o 'isError' en el ViewModel para ser más preciso)
                // Por ahora, asumimos que si llega un false aquí fue por fallo de credenciales.
                Toast.makeText(requireContext(), "RUT o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}