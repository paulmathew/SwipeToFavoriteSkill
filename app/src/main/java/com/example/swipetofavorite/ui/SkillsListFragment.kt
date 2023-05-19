package com.example.swipetofavorite.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetofavorite.R
import com.example.swipetofavorite.databinding.ActivityListBinding
import com.example.swipetofavorite.model.SkillList
import com.example.swipetofavorite.utils.DialogUtils
import com.example.swipetofavorite.utils.FavoriteFlag
import com.example.swipetofavorite.viewmodel.MainViewModel
import com.example.swipetofavorite.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_list.swipeFavList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class SkillsListFragment @Inject constructor(
    private val mainViewModelFactory: MainViewModelFactory
) : Fragment() {

    private var _binding: ActivityListBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var skillList: SkillList
    private lateinit var skillListAdapter: SkillListAdapter
    private val binding get() = _binding!!
    private var currentSkillItemPos = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        _binding = ActivityListBinding.inflate(inflater, container, false)

        skillList = SkillList(skills = arrayListOf())
        // adapter initialization and onClick handling
        skillListAdapter =
            SkillListAdapter(arrayListOf(), OnClickListener { item, position, isMenu ->

                if (isMenu) {
                    currentSkillItemPos = position
                    when (item.isFavorite) {
                        true -> {
                            mainViewModel.removeFavoriteSkill(skillName = item.tileName.toString())
                        }

                        false -> {
                            mainViewModel.addToFavoriteSkill(
                                skillName = item.tileName.toString(),
                                dictionaryName = item.dictionaryName.toString()
                            )
                        }

                        else -> {}
                    }
                }


            })


        binding.swipeFavList.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = skillListAdapter
        }
        binding.swipeFavList.recycledViewPool.setMaxRecycledViews(1, 0)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            mainViewModel.fetchSkillList()
        }


        observeViewModel()



        return binding.root
    }

    private fun observeViewModel() {
        // observing api call response
        mainViewModel.skillList.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (response.skills.isEmpty() && response.errors?.isNotEmpty() == true) {
                    context?.let { ctx ->
                        DialogUtils.showAlert(
                            context = ctx,
                            getString(R.string.label_warning),
                            response.errors.first().message,
                            positiveButtonText = getString(R.string.label_ok)
                        ) { value ->
                            when (value) {
                                DialogUtils.positiveButton -> {

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
        //observing is loading live data to show the loader
        mainViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    swipeFavList.visibility = View.GONE
                }
            }
        }

        // observing fetch api error for displaying it
        mainViewModel.skillListLoadError.observe(viewLifecycleOwner) { errorMsg ->
            context?.let { ctx ->
                DialogUtils.showAlert(
                    context = ctx,
                    getString(R.string.label_warning),
                    errorMsg,
                    positiveButtonText = getString(R.string.label_ok)
                ) { value ->
                    when (value) {
                        DialogUtils.positiveButton -> {

                        }
                    }
                }
            }
        }


        // remove api loader
        mainViewModel.loadingRemoveFav.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
            }

        }

        // remove from fav api response observing
        mainViewModel.removeFavResponse.observe(viewLifecycleOwner) { response ->

            if (response == "removed") {
                CoroutineScope(Dispatchers.Main).launch {
                    skillListAdapter.changeMenuItemData(
                        currentSkillItemPos,
                        FavoriteFlag.Removed
                    )
                }
            } else {
                context?.let { ctx ->
                    DialogUtils.showAlert(
                        context = ctx,
                        getString(R.string.label_warning),
                        response,
                        positiveButtonText = getString(R.string.label_ok)
                    ) { value ->
                        when (value) {
                            DialogUtils.positiveButton -> {

                            }
                        }
                    }
                }
            }
        }


        // add to api loader
        mainViewModel.loadingAddFav.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
            }

        }

        // observing add to fav api response
        mainViewModel.addToResponse.observe(viewLifecycleOwner) { response ->

            if (response == "added") {
                CoroutineScope(Dispatchers.Main).launch {
                    skillListAdapter.changeMenuItemData(
                        currentSkillItemPos,
                        FavoriteFlag.Added
                    )
                }

            } else {
                context?.let { ctx ->
                    DialogUtils.showAlert(
                        context = ctx,
                        getString(R.string.label_warning),
                        response,
                        positiveButtonText = getString(R.string.label_ok)
                    ) { value ->
                        when (value) {
                            DialogUtils.positiveButton -> {

                            }
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