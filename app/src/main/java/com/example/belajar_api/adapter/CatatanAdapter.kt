package com.example.belajar_api.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belajar_api.databinding.ItemCatatanBinding
import com.example.belajar_api.entity.Catatan

class CatatanAdapter (

    private val dataset: MutableList<Catatan>

): RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {
    inner class CatatanViewHolder(
        val view: ItemCatatanBinding
    ): RecyclerView.ViewHolder(view.root) {
        fun setDataUI(data: Catatan) {
            view.title.text = data.title
            view.content.text = data.content
            view.userId.text = data.user_id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val binding = ItemCatatanBinding.inflate(
        LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CatatanViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val dataSekarang = dataset[position]
        holder.setDataUI(dataSekarang)
    }

    @SuppressLint("NotifyDataSetChaged")
    fun updateDataset(dataBaru: List<Catatan>) {
        dataset.clear()
        dataset.addAll(dataBaru)
        notifyDataSetChanged()
    }
}
