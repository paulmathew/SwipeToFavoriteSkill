package com.example.swipetofavorite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.swipetofavorite.viewmodel.MainViewModel
import com.example.swipetofavorite.viewmodel.MainViewModelFactory
import java.util.ArrayList
import javax.inject.Inject


class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory // Dagger will provide the object to this variable through field injection



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        (application as SwipeToFavApplication).applicationComponent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]


    }
}