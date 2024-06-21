package com.capstone.pulih.ui.konselormitra

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pulih.data.response.KonselorResponse
import com.capstone.pulih.databinding.ListKonselorBinding

class AdapterListKonselor(private val konselor : List<KonselorResponse>) :
    RecyclerView.Adapter<AdapterListKonselor.ListKonselorViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListKonselorViewHolder {
        val view = ListKonselorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListKonselorViewHolder(view)
    }

    override fun getItemCount(): Int = konselor.size

    override fun onBindViewHolder(holder: ListKonselorViewHolder, position: Int) {
        val entry = konselor[position]
        holder.bind(entry)
    }

    inner class ListKonselorViewHolder(val binding: ListKonselorBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(entry : KonselorResponse){
            binding.apply {
                binding.tvNameCouns.text = entry.nama
                binding.tvMitraCouns.text = entry.mitra
            }
        }
    }
    }