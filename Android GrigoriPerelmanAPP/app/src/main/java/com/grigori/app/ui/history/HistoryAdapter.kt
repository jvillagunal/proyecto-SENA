package com.grigori.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grigori.app.data.local.entities.Problema
import com.grigori.app.databinding.ItemHistoryBinding

class HistoryAdapter(private val onClick: (Problema) -> Unit) : ListAdapter<Problema, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(problem: Problema) {
            binding.tvTitle.text = problem.ecuacion
            binding.tvSubtitle.text = "Resultado: ${problem.resultado}"
            binding.root.setOnClickListener { onClick(problem) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Problema>() {
        override fun areItemsTheSame(oldItem: Problema, newItem: Problema): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Problema, newItem: Problema): Boolean = oldItem == newItem
    }
}
