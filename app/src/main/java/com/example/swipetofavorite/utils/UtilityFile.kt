package com.example.swipetofavorite.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.swipetofavorite.R
import com.example.swipetofavorite.SwipeToFavApplication
import com.example.swipetofavorite.databinding.ItemSkillListBinding
import java.text.SimpleDateFormat
import java.util.Date


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_loading_error)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@SuppressLint("SimpleDateFormat")
fun getDateTime(timeStamp: String): String {
    return SimpleDateFormat("hh:mm a").format(Date(timeStamp.toLong())).toString()

}

val isNetworkConnected: Boolean
    get() {
        var result = false
        val connectivityManager =
            SwipeToFavApplication.ctx?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)


        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

enum class FavoriteFlag{
    AddTo,
    Added,
    Remove,
    Removed
}

//for getting the actual width of a view after loading fully
fun View.afterLayout(what: () -> Unit) {
    if(isLaidOut) {
        what.invoke()
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                what.invoke()
            }
        })
    }
}

fun showMenu(binding: ItemSkillListBinding) {
    binding.menuView.afterLayout {
        AdditiveAnimator().target(binding.menuView).setDuration(300).fadeVisibility(View.VISIBLE)
            .target(binding.parentCard).translationX(-binding.menuView.width.toFloat())
            .target(binding.availabilityButton).translationX(-binding.menuView.width.toFloat())
            .target(binding.menuView).translationX(0f).start()
    }
}

fun hideMenu(binding: ItemSkillListBinding) {
    binding.menuView.afterLayout {
        AdditiveAnimator().setDuration(300).target(binding.menuView)
            .translationX(binding.menuView.width.toFloat())
            .target(binding.parentCard).translationX(0f)
            .target(binding.availabilityButton).translationX(0f)
            .target(binding.menuView).fadeVisibility(View.INVISIBLE).start()
    }


}

// this function is to set the margin data to the stacked images in a skill list item
fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
    if (view.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = view.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        view.requestLayout()
    }
}

