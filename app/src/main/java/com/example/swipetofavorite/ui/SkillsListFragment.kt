package com.example.swipetofavorite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.swipetofavorite.databinding.ActivityListBinding
import com.example.swipetofavorite.retrofit.SwipeToFavApi
import com.example.swipetofavorite.viewmodel.MainViewModel
import com.example.swipetofavorite.viewmodel.MainViewModelFactory
import javax.inject.Inject

class SkillsListFragment @Inject constructor(mainViewModelFactory:MainViewModelFactory) : Fragment() {



    private var _binding: ActivityListBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory:MainViewModelFactory

    private val binding get() = _binding!!

    init {
        this.mainViewModelFactory=mainViewModelFactory
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]


        //
        _binding = ActivityListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}