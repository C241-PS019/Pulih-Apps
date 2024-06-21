package com.capstone.pulih.ui.artikel


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pulih.data.response.NewsResponse
import com.capstone.pulih.databinding.ListNewsBinding

class AdapterNews(private val berita : List<NewsResponse>) :
    RecyclerView.Adapter<AdapterNews.ListNewsViewHolder>(){

    inner class ListNewsViewHolder(val binding: ListNewsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(entry : NewsResponse){
            binding.apply {
                binding.tvTitle.text = entry.title
                binding.tvAuthor.text = "Author: ${entry.author}"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterNews.ListNewsViewHolder {
        val view = ListNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListNewsViewHolder(view)
    }

    override fun getItemCount(): Int = berita.size

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        val entry = berita[position]
        holder.bind(entry)
    }
}