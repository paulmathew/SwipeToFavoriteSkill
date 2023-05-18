package com.example.swipetofavorite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetofavorite.databinding.ItemCircleImageBinding
import com.example.swipetofavorite.model.ProviderInfo
import com.example.swipetofavorite.utils.getProgressDrawable
import com.example.swipetofavorite.utils.loadImage

class ImageListAdapter(private var imagesList: MutableList<ProviderInfo>) :
    RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCircleImageBinding.inflate(inflater, parent, false)
        return ImageListViewHolder(binding)
    }

    override fun getItemCount() = imagesList.size

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(imagesList[position])
    }

    class ImageListViewHolder(private val binding: ItemCircleImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = getProgressDrawable(binding.root.context)

        fun bind(image: ProviderInfo) {

            binding.profileImg.loadImage(image.profileImage, progressDrawable)

        }
    }


}