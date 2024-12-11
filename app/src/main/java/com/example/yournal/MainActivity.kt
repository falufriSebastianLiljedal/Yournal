package com.example.yournal

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.yournal.databinding.ActivityMainBinding
import com.example.yournal.Trip
import com.example.yournal.TripManager



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var tripManager: TripManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(savedInstanceState == null) {
            val addTripFragment = AddTripFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, addTripFragment)
                .commit()
        }

        binding.btnImgList.setOnClickListener{
            val listFrag = TripListFragment.newInstance(

            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, listFrag)
                .addToBackStack(null)
                .commit()
            Log.d("supportFragment", "Listfragment started")
        }
        binding.btnImgAdd.setOnClickListener{
            val addFrag = AddTripFragment.newInstance(
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, addFrag)
                .addToBackStack(null)
                .commit()
        }
        binding.btnImgSettings.setOnClickListener{
            val settingsFrag = SettingsFragment.newInstance(
                "Test", "ing"
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, settingsFrag)
                .addToBackStack(null)
                .commit()
        }
    }
}