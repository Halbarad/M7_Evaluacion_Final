package cl.unab.m7_evaluacion_final.labor_forum.ui.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.unab.m7_evaluacion_final.databinding.ItemOfertaLaboralBinding
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import java.text.NumberFormat
import java.util.Locale

// Usamos ListAdapter que es más moderno y eficiente que RecyclerView.Adapter estándar
class OfertaLaboralAdapter(
    private val onOfertaClick: (OfertaLaboral) -> Unit // Lambda para manejar el clic
) : ListAdapter<OfertaLaboral, OfertaLaboralAdapter.OfertaViewHolder>(OfertaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val binding = ItemOfertaLaboralBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfertaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = getItem(position)
        holder.bind(oferta)
    }

    inner class OfertaViewHolder(private val binding: ItemOfertaLaboralBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(oferta: OfertaLaboral) {
            // Asignamos los datos a las vistas (Asegúrate de que estos IDs existan en tu item_oferta_laboral.xml)
            binding.tvTituloOferta.text = oferta.titulo
            binding.tvNombreEmpresa.text = oferta.empresa
            binding.tvUbicacion.text = "${oferta.comuna}, ${oferta.region}"

            // Formato moneda
            try {
                val formato = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                formato.maximumFractionDigits = 0
                binding.tvSalario.text = formato.format(oferta.salario.toLong())
            } catch (e: Exception) {
                binding.tvSalario.text = "${oferta.salario}"
            }

            // Click en toda la tarjeta
            binding.root.setOnClickListener {
                onOfertaClick(oferta)
            }
        }
    }

    class OfertaDiffCallback : DiffUtil.ItemCallback<OfertaLaboral>() {
        override fun areItemsTheSame(oldItem: OfertaLaboral, newItem: OfertaLaboral): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OfertaLaboral, newItem: OfertaLaboral): Boolean {
            return oldItem == newItem
        }
    }
}