package cl.unab.m7_evaluacion_final.labor_forum.ui.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.unab.m7_evaluacion_final.databinding.ItemMiOfertaBinding // Asegúrate de que este Binding exista
import cl.unab.m7_evaluacion_final.labor_forum.modelos.OfertaLaboral
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MisOfertasAdapter(
    private val onOfertaClick: (OfertaLaboral) -> Unit
) : ListAdapter<OfertaLaboral, MisOfertasAdapter.MiOfertaViewHolder>(OfertaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiOfertaViewHolder {
        val binding = ItemMiOfertaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MiOfertaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MiOfertaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MiOfertaViewHolder(private val binding: ItemMiOfertaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(oferta: OfertaLaboral) {
            // Ajusta estos IDs según tu archivo 'item_mi_oferta.xml'
            binding.tvTituloOferta.text = oferta.titulo
            binding.tvNombreEmpresa.text = oferta.empresa
            binding.tvUbicacion.text = "${oferta.comuna}, ${oferta.region}"

            try {
                val formato = NumberFormat.getCurrencyInstance(Locale.getDefault())
                formato.maximumFractionDigits = 0
                binding.tvSalario.text = formato.format(oferta.salario.toLong())
            } catch (e: Exception) {
                binding.tvSalario.text = "$ ${oferta.salario}"
            }

//            // Formatear Fecha Término
//            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            val fechaTermino = formato.format(oferta.fechaTerminoContrato)
//            binding.tvFechaTermino.text = "Termina: $fechaTermino"

            // Click
            binding.root.setOnClickListener { onOfertaClick(oferta) }
        }
    }

    class OfertaDiffCallback : DiffUtil.ItemCallback<OfertaLaboral>() {
        override fun areItemsTheSame(oldItem: OfertaLaboral, newItem: OfertaLaboral) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: OfertaLaboral, newItem: OfertaLaboral) = oldItem == newItem
    }
}