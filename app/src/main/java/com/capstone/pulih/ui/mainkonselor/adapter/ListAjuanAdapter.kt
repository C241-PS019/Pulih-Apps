package com.capstone.pulih.ui.mainkonselor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.databinding.ListAjuanBinding
import com.capstone.pulih.ui.mainkonselor.konseling.DetailAjuanActivity
import com.capstone.pulih.utils.Preferences

class ListAjuanAdapter(private var pengajuanEntries: List<PengajuanResponse>) :
    RecyclerView.Adapter<ListAjuanAdapter.PengajuanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PengajuanViewHolder {
        val binding = ListAjuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PengajuanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PengajuanViewHolder, position: Int) {
        val entry = pengajuanEntries[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int = pengajuanEntries.size

    fun updateEntries(newEntries: List<PengajuanResponse>) {
        pengajuanEntries = newEntries
        notifyDataSetChanged()
    }

    inner class PengajuanViewHolder(private val binding: ListAjuanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: PengajuanResponse) {

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailAjuanActivity::class.java).apply{
                    putExtra(DetailAjuanActivity.AJUAN, entry)
                }
                context.startActivity(intent)
            }

            binding.nameUser.text = entry.namaPengguna
            binding.date.text = entry.tanggal
            binding.status.text = entry.status

        }
    }


}
