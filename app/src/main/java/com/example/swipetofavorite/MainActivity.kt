package com.example.swipetofavorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.swipetofavorite.databinding.ActivityMainBinding
import com.example.swipetofavorite.ui.ComingSoonFragment
import com.example.swipetofavorite.ui.SkillsListFragment
import com.example.swipetofavorite.viewmodel.MainViewModel
import com.example.swipetofavorite.viewmodel.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory // Dagger will provide the object to this variable through field injection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        (application as SwipeToFavApplication).applicationComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.navView
        loadFragment(SkillsListFragment(mainViewModelFactory))

        navigationItemSelect(bottomNav)

    }

    private fun navigationItemSelect(bottomNav: BottomNavigationView) {
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_discover -> {
                    loadFragment(SkillsListFragment(mainViewModelFactory))
                    true
                }

                R.id.navigation_reconnect -> {
                    loadFragment(ComingSoonFragment())
                    true
                }

                R.id.navigation_bookings -> {
                    loadFragment(ComingSoonFragment())
                    true
                }

                R.id.navigation_message -> {
                    loadFragment(ComingSoonFragment())
                    true
                }

                R.id.navigation_blogs -> {
                    loadFragment(ComingSoonFragment())
                    true
                }

                else -> {
                    loadFragment(ComingSoonFragment())
                    true
                }
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}