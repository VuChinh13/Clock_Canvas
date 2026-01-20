package com.example.firstapp.testservice.ui

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.FragmentHomeMusicBinding
import com.example.firstapp.testservice.FileAdapter
import com.example.firstapp.testservice.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeMusicFragment : Fragment() {
    private lateinit var binding: FragmentHomeMusicBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: FileAdapter
    private val TAG = "Check"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        scanFiles()
    }

    private fun initAdapter() {
        recyclerView = binding.rvMain
        fileAdapter = FileAdapter(mutableListOf()) { file ->
            switchPlayMusicFragment(file)
        }
        recyclerView.adapter = fileAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun scanFiles() {
        lifecycleScope.launch(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.MIME_TYPE
            )
            val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
            val cursorAudio = requireContext().applicationContext.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            val newFileItems = mutableListOf<FileItem>()
            cursorAudio?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
                    val fileItem =
                        FileItem(name, "content://media/external/audio/media/$id")
                    Log.d(TAG, fileItem.path)
                    newFileItems.add(fileItem)
                }
            }

            withContext(Dispatchers.Main) {
                fileAdapter.updateData(newFileItems)
            }
        }
    }

    fun switchPlayMusicFragment(file: FileItem) {
        (activity as? MainMusicActivity)?.switchPlayMusicFragment(file)
    }
}