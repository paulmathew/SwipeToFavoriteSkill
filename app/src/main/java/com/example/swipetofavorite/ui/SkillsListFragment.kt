package com.example.swipetofavorite.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetofavorite.R
import com.example.swipetofavorite.databinding.ActivityListBinding
import com.example.swipetofavorite.model.SkillList
import com.example.swipetofavorite.utils.DialogUtils
import com.example.swipetofavorite.viewmodel.MainViewModel
import com.example.swipetofavorite.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_list.swipeFavList
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

        skillList = SkillList(skills = arrayListOf())
        skillListAdapter = SkillListAdapter(arrayListOf(), OnClickListener { item ->
             Toast.makeText(activity, "Clicked Skill is " + item.tileName, Toast.LENGTH_LONG).show()

        })

        binding.swipeFavList.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = skillListAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            mainViewModel.fetchSkillList()
        }
        observeViewModel()
        binding.swipeFavList.addOnItemTouchListener(
            object : RecyclerView.OnItemTouchListener {
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onInterceptTouchEvent(
                    rv: RecyclerView, e:
                    MotionEvent
                ): Boolean {
                    if (e.action == MotionEvent.ACTION_DOWN &&
                        rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING
                    ) {
                        rv.stopScroll()
                    }
                    return false
                }

                override fun onRequestDisallowInterceptTouchEvent(
                    disallowIntercept: Boolean
                ) {
                }
            })


        return binding.root
    }

    private fun observeViewModel() {
        mainViewModel.skillList.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (response.skills.isEmpty() && response.errors?.isNotEmpty() == true) {
                    context?.let { ctx ->
                        DialogUtils.showAlert(
                            context = ctx,
                            getString(R.string.label_warning),
                            response.errors.first().message,
                            positiveButtonText = getString(R.string.label_ok)
                        ){
                            value->
                            when(value)
                            {
                                DialogUtils.positiveButton->{

                                }
                            }
                        }
                    }
                } else {
                    binding.swipeFavList.visibility = View.VISIBLE
                    skillList = it
                    binding.item = skillList
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
        mainViewModel.skillListLoadError.observe(viewLifecycleOwner) { errorMsg ->
            context?.let { ctx ->
                DialogUtils.showAlert(
                    context = ctx,
                    getString(R.string.label_warning),
                    errorMsg,
                    positiveButtonText = getString(R.string.label_ok)
                ){
                        value->
                    when(value)
                    {
                        DialogUtils.positiveButton->{

                        }
                    }
                }
            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}