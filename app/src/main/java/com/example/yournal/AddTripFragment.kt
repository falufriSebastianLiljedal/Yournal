package com.example.yournal

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.yournal.databinding.ActivityMainBinding
import com.example.yournal.databinding.FragmentAddTripBinding
import java.time.Instant
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


class AddTripFragment : Fragment() {

    private  var _binding: FragmentAddTripBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTripBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnRince.setOnClickListener{
            rinse()
        }
        binding.btnCreate.setOnClickListener{
            createNewPost()
            rinse()
        }

        binding.editTextTo.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkIfCreateEnable()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.editTextFrom.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkIfCreateEnable()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.editTextValueFrom.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkIfCreateEnable()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.editTextValueTo.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkIfCreateEnable()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            AddTripFragment().apply {

            }
    }

    private fun rinse(){
        binding.editTextTo.setText("")
        binding.editTextFrom.setText("")
        binding.checkBox.isChecked = false
        binding.editTextTime.setText("")
        binding.editTextValueTo.setText("")
        binding.calendarView.date = Instant.now().toEpochMilli()
        binding.editTextText3.setText("")
    }

    private fun checkIfCreateEnable() {
        var fromValue = 0<binding.editTextValueFrom.length()
        var toValue = 0<binding.editTextValueTo.length()
        var from = 0<binding.editTextFrom.length()
        var to = 0<binding.editTextTo.length()

        binding.btnCreate.isEnabled = (fromValue && toValue && from && to)
    }

    private fun createNewPost(){
        var id = TripManager.getNextId()
        var toValue = binding.editTextValueTo.text.toString().toInt()
        var endValue = binding.editTextValueTo.text.toString().toInt()
        var date = Date(binding.calendarView.date)
        var company = binding.checkBox.isChecked
        var from = binding.editTextFrom.text.toString()
        var to = binding.editTextTo.text.toString()
        var desc = binding.editTextText3.toString()

        var trip = Trip(id,toValue,endValue, date, company, from, to, desc)

        TripManager.AddTrip(trip)

    }
}