package com.example.firstapp.testservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.ItemFileBinding

class FileAdapter(
    private val listFile: MutableList<FileItem>,
    private val switchPlayMusicFragment: (FileItem) -> Unit
) :
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    private lateinit var binding: ItemFileBinding

    class FileViewHolder(binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        val fileName = binding.tvFileName
        val fileIcon = binding.ivFileIcon
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FileViewHolder {
        binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FileViewHolder,
        position: Int
    ) {
        val file = listFile[position]
        with(holder) {
            fileName.text = file.name

            itemView.setOnClickListener {
                switchPlayMusicFragment(file)
            }
        }
    }

    override fun getItemCount(): Int {
        return listFile.size
    }

    fun updateData(listFileNew: MutableList<FileItem>) {
        listFile.clear()
        listFile.addAll(listFileNew)
        notifyDataSetChanged()
    }
}