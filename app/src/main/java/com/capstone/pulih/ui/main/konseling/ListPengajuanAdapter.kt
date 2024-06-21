package com.capstone.pulih.ui.main.konseling

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.databinding.ListPengajuanBinding
import com.capstone.pulih.ui.mainkonselor.konseling.DetailAjuanActivity

class ListPengajuanAdapter(
    private var pengajuanEntries: List<PengajuanResponse>
) : RecyclerView.Adapter<ListPengajuanAdapter.PengajuanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PengajuanViewHolder {
        val view = ListPengajuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PengajuanViewHolder(view)

    }

    override fun onBindViewHolder(holder: PengajuanViewHolder, position: Int) {
        val entry = pengajuanEntries[position]
        holder.bind(entry)
    }

    fun updateEntries(newEntries: List<PengajuanResponse>) {
        pengajuanEntries = newEntries
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = pengajuanEntries.size

    inner class PengajuanViewHolder(private val binding: ListPengajuanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: PengajuanResponse) {

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailPengajuanActivity::class.java).apply{
                    putExtra(DetailPengajuanActivity.PENGAJUAN, entry)
                }
                context.startActivity(intent)
            }
            binding.nameKonselor.text = entry.namaKonselor
            binding.date.text = entry.tanggal
            binding.status.text = entry.status

        }
    }


}
