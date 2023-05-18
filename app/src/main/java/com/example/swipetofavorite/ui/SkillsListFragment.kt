package com.example.swipetofavorite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipetofavorite.databinding.ActivityListBinding
import com.example.swipetofavorite.model.SkillList
import com.example.swipetofavorite.viewmodel.MainViewModel
import com.example.swipetofavorite.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_list.loading_view
import kotlinx.android.synthetic.main.activity_list.swipeFavList
import kotlinx.android.synthetic.main.activity_list.swipeRefreshLayout
import java.util.ArrayList
import javax.inject.Inject

class SkillsListFragment @Inject constructor(
    private val mainViewModelFactory: MainViewModelFactory
) : Fragment() {

    private var _binding: ActivityListBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var skillList: SkillList
    private lateinit var skillListAdapter: SkillListAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        _binding = ActivityListBinding.inflate(inflater, container, false)

        skillList=SkillList(skills = arrayListOf())
        skillListAdapter = SkillListAdapter(arrayListOf(), OnClickListener { item ->
            Toast.makeText(activity, "Clicked Skill is " + item.tileName, Toast.LENGTH_LONG).show()

        })

        binding.swipeFavList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = skillListAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            mainViewModel.fetchSkillList()
        }
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        mainViewModel.skillList.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (response.skills.isNullOrEmpty()) {
                    Log.e("observer error", response.errors?.first()?.message.toString())
                } else {
                    binding.swipeFavList.visibility = View.VISIBLE
                    skillList = it
                    binding.item=skillList
                    skillListAdapter.updateSkills(skillList.skills)
                }
            }

        }
        mainViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    swipeFavList.visibility = View.GONE
                }
            }
        }
    }


    override fun getUserVisibleHint(): Boolean {
        return super.getUserVisibleHint()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}