package com.example.swipetofavorite.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.swipetofavorite.R
import com.example.swipetofavorite.databinding.ItemSkillListBinding
import com.example.swipetofavorite.model.Skill
import com.example.swipetofavorite.utils.OnSwipeTouchListener
import com.example.swipetofavorite.utils.getProgressDrawable


class SkillListAdapter(
    private var skillList: MutableList<Skill>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<SkillListAdapter.SkillViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun updateSkills(newSkills: MutableList<Skill>) {
        skillList.clear()
        skillList.addAll(newSkills)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSkillListBinding.inflate(inflater, parent, false)
        return SkillViewHolder(binding)
    }

    override fun getItemCount() = skillList.size

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.bind(skillList[position])
        holder.itemView.setOnClickListener { onClickListener.onClick(skillList[position]) }
    }

    class SkillViewHolder(private val binding: ItemSkillListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var isMenuVisible = false

        @SuppressLint("ClickableViewAccessibility")
        fun bind(skill: Skill) {
            binding.menuView.translationX=binding.menuView.width.toFloat()

            if (!isMenuVisible) {
                hideMenu()
            }
            binding.parentView.setOnTouchListener(object :
                OnSwipeTouchListener(binding.root.context) {

                override fun onSwipeRight() {
                    hideMenu()
                    Log.e("Swipe ", "Right")
                }

                override fun onSwipeLeft() {
                    Log.e("Swipe ", "Left")

                    showMenu()
                }
            })
            binding.item = skill
            when (skill.availability?.status) {
                0 -> {
                    binding.availabilityButton.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_list_item_top_green
                    )
                }

                1 -> {
                    binding.availabilityButton.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_list_item_top_yellow
                    )
                }

                2 -> {
                    binding.availabilityButton.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_list_item_top_grey
                    )
                }

                else -> {
                    binding.availabilityButton.visibility = View.INVISIBLE
                }
            }

            if (!skill.tileColor.isNullOrEmpty()) {
                binding.parentCard.setCardBackgroundColor(Color.parseColor(skill.tileColor))
            }
            if (!skill.providerInfo.isNullOrEmpty()) {
                binding.imagesRecyclerView.adapter = ImageListAdapter(skill.providerInfo)
                binding.imagesRecyclerView.layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.imagesRecyclerView.setHasFixedSize(true)
                binding.imagesRecyclerView.addItemDecoration(ItemDecorator())
            }
            binding.imagesRecyclerView.suppressLayout(true)
         if(!skill.availability?.color.isNullOrEmpty())
            binding.timestampView.setTextColor(Color.parseColor(skill.availability?.color))

        }

        fun showMenu() {

            binding.menuView.animate()
                .translationX(0F)
                .alpha(1F)
                .setDuration(250)
                .withEndAction {   binding.menuView.visibility = View.VISIBLE }
                .start()




            binding.parentCard.animate()
                .translationX((-binding.menuView.width).toFloat())
                .setDuration(500)
                .start()

            binding.availabilityButton.animate()
                .translationX((-binding.menuView.width).toFloat())
                .setDuration(500)
                .start()

            isMenuVisible = true
        }

         fun hideMenu() {
            //  binding.menuView.visibility = View.GONE
         binding.menuView.animate()
             .translationX(binding.menuView.width.toFloat())
             .setDuration(300)
             .withEndAction { binding.menuView.visibility=View.INVISIBLE }
             .start()


            binding.parentCard.animate()
                .translationX(0F)
                .setDuration(300)
                .start()
            binding.availabilityButton.animate()
                .translationX(0F)
                .setDuration(300)
                .start()
            isMenuVisible = false
        }

        fun closeSwipeMenu() {
            if (isMenuVisible) {
                hideMenu()
            }
        }
    }


}


class ItemDecorator() : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            val widthOverlapPercentage = 0.50
            val previousView = parent[position - 1]
            val overlapWidth = previousView.width * widthOverlapPercentage
            outRect.left = overlapWidth.toInt() * -1
        }
    }
}


class OnClickListener(val clickListener: (skill: Skill) -> Unit) {
    fun onClick(skill: Skill) = clickListener(skill)
}