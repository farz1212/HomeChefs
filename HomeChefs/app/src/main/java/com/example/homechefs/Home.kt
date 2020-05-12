package com.example.homechefs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

        bottombar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
