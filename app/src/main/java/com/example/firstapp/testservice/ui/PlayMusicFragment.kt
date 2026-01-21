package com.example.firstapp.testservice.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstapp.databinding.FragmentPlayMusicBinding
import com.example.firstapp.testservice.FileItem
import com.example.firstapp.testservice.IntentExtras
import com.example.firstapp.testservice.MusicService

class PlayMusicFragment : Fragment() {
    private lateinit var binding: FragmentPlayMusicBinding
    private var currentIndex = 0
    private var fileList = mutableListOf<FileItem>()
    private var file: FileItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        file = arguments?.getParcelable(IntentExtras.EXTRA_FILE, FileItem::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTen.text = file?.name
        currentIndex = fileList.indexOfFirst { it.path == file?.path }
        playMusic()
    }

    private fun playMusic() {
        val musicServiceIntent = Intent(requireContext(), MusicService::class.java)
        musicServiceIntent.putExtra(IntentExtras.EXTRA_FILE, file)
        requireContext().startForegroundService(musicServiceIntent)
    }
}