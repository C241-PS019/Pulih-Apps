package com.capstone.pulih.ui.journaling.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pulih.data.response.JournalResponseMorning
import com.capstone.pulih.databinding.ListJournalingBinding
import com.capstone.pulih.ui.journaling.ui.CreateJournalingEveningActivity
import com.capstone.pulih.ui.journaling.ui.CreateJournalingMorningActivity

class JournalMorningAdapter(private val journalEntries: List<JournalResponseMorning>) :
    RecyclerView.Adapter<JournalMorningAdapter.JournalEntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalEntryViewHolder {
        val view = ListJournalingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalEntryViewHolder, position: Int) {
        val entry = journalEntries[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int = journalEntries.size

    inner class JournalEntryViewHolder(val binding: ListJournalingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: JournalResponseMorning) {
            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, CreateJournalingMorningActivity::class.java).apply {
                    putExtra(CreateJournalingMorningActivity.EXTRA_JOURNAL_ENTRY, entry)
                }
                context.startActivity(intent)
            }
            binding.apply {
                binding.date.text = entry.tanggal
                binding.title.text = entry.judul
            }
        }
    }

}
