package com.example.swipetofavorite.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetofavorite.R
import com.example.swipetofavorite.databinding.ItemSkillListBinding
import com.example.swipetofavorite.model.Skill
import com.example.swipetofavorite.utils.OnSwipeTouchListener
import com.example.swipetofavorite.utils.FavoriteFlag
import com.example.swipetofavorite.utils.getProgressDrawable
import com.example.swipetofavorite.utils.hideMenu
import com.example.swipetofavorite.utils.loadImage
import com.example.swipetofavorite.utils.setMargins
import com.example.swipetofavorite.utils.showMenu
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("NotifyDataSetChanged")
class SkillListAdapter(
    private var skillList: MutableList<Skill>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<SkillListAdapter.SkillViewHolder>() {

    var previousSwipePos = 0
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


        // for row item swipe
        holder.itemView.setOnTouchListener(object :
            OnSwipeTouchListener(holder.itemView.context) {

            override fun onSwipeRight() {
                skillList[previousSwipePos].isSwiped = false
                notifyDataSetChanged()
            }

            override fun onSwipeLeft() {

                if (skillList[previousSwipePos].isSwiped) {
                    skillList[previousSwipePos].isSwiped = false
                }
                previousSwipePos = holder.layoutPosition
                skillList[previousSwipePos].isSwiped = true
                notifyDataSetChanged()

            }

        })

        // for row item click or skill item click
        holder.bindingItem.parentView.setOnClickListener {}
        // for menu item click
        holder.bindingItem.menuView.setOnClickListener {
            onClickListener.onClick(
                skillList[holder.layoutPosition],
                holder.layoutPosition,
                isMenu = true
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }
    suspend fun changeMenuItemData(pos: Int, flag: FavoriteFlag) {
        coroutineScope {

            launch {
                skillList[pos].currentStateFlag = flag
                skillList[pos].isSwiped = true
                notifyItemChanged(pos)
                delay(3000)
                when (flag) {
                    FavoriteFlag.Added -> {
                        skillList[pos].currentStateFlag = FavoriteFlag.Remove
                        skillList[pos].isFavorite = true
                    }

                    FavoriteFlag.Removed -> {
                        skillList[pos].currentStateFlag = FavoriteFlag.AddTo
                        skillList[pos].isFavorite = false
                    }

                    else -> {}
                }

                skillList[pos].isSwiped = false
                notifyItemChanged(pos)
            }
        }

    }


    class SkillViewHolder(private val binding: ItemSkillListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var bindingItem: ItemSkillListBinding
        private val progressDrawable = getProgressDrawable(binding.root.context)



        @SuppressLint("ClickableViewAccessibility")
        fun bind(skill: Skill) {
            binding.item = skill
            bindingItem = binding

            if (skill.isSwiped) {
                showMenu(binding)

            } else {

                hideMenu(binding)
            }



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


            if (!skill.providerInfo.isNullOrEmpty()) {
                binding.listOfImages.removeAllViews()
                for (i in skill.providerInfo.indices) {
                    val item = skill.providerInfo[i]
                    val itemView = LayoutInflater.from(binding.root.context)
                        .inflate(R.layout.item_circle_image, binding.listOfImages, false)
                    val imageView = itemView.findViewById<ShapeableImageView>(R.id.profileImg)
                    imageView.loadImage(item.profileImage, progressDrawable)

                    val overlapAmount = 10
                    if (i != 0) {
                        imageView.translationX = overlapAmount.toFloat()
                        setMargins(imageView, -50, 0, 0, 0)
                    }
                    if (i < 4)
                        binding.listOfImages.addView(imageView)

                }
            }



            if (!skill.availability?.color.isNullOrEmpty())
                binding.timestampView.setTextColor(Color.parseColor(skill.availability?.color))

            when (skill.isFavorite) {
                true -> {
                    changeMenuIcon(FavoriteFlag.Remove)
                }

                false -> {
                    changeMenuIcon(FavoriteFlag.AddTo)
                }

                else -> {}
            }

            when (skill.currentStateFlag) {
                FavoriteFlag.Added -> {
                    changeMenuIcon(FavoriteFlag.Added)
                    skill.currentStateFlag = FavoriteFlag.Remove
                }

                FavoriteFlag.Removed -> {
                    changeMenuIcon(FavoriteFlag.Removed)
                    skill.currentStateFlag = FavoriteFlag.AddTo
                }

                else -> {}
            }
        }


        private fun changeMenuIcon(typeFlag: FavoriteFlag) {
            when (typeFlag) {
                FavoriteFlag.AddTo -> {
                    binding.menuOption.text =
                        binding.root.context.getString(R.string.label_add_to_favorite)
                    binding.menuOption.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.ic_heart_swipe_to,
                        0,
                        0
                    )

                }

                FavoriteFlag.Added -> {
                    binding.menuOption.text =
                        binding.root.context.getString(R.string.label_added_to_fav)
                    binding.menuOption.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.ic_added_to_fav,
                        0,
                        0
                    )
                }

                FavoriteFlag.Remove -> {
                    binding.menuOption.text =
                        binding.root.context.getString(R.string.label_remove_favorite)
                    binding.menuOption.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.ic_remove_from,
                        0,
                        0
                    )
                }

                FavoriteFlag.Removed -> {
                    binding.menuOption.text =
                        binding.root.context.getString(R.string.label_removed)
                    binding.menuOption.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.ic_remove_from,
                        0,
                        0
                    )
                }
            }
        }
    }
}

class OnClickListener(val clickListener: (skill: Skill, pos: Int, isMenu: Boolean) -> Unit) {
    fun onClick(skill: Skill, pos: Int, isMenu: Boolean) = clickListener(skill, pos, isMenu)
}


