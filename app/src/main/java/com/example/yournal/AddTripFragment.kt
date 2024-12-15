package com.example.yournal

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yournal.databinding.FragmentAddTripBinding
import java.time.Instant
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class AddTripFragment : Fragment() {

    private  var _binding: FragmentAddTripBinding? = null
    private val binding get() = _binding!!

    private var calender = Calendar.getInstance(
        TimeZone.getTimeZone("Europe/Stockholm"), Locale("sv", "SE")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTripBinding.inflate(inflater, container, false)
        val view = binding.root

        calender.timeZone
        calender.set(Calendar.HOUR_OF_DAY, 2)
        calender.set(Calendar.MINUTE, 0)
        calender.set(Calendar.SECOND, 0)
        calender.set(Calendar.MILLISECOND, 0)



        updateFromValue()

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

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        }
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            AddTripFragment().apply {

            }
    }

    @SuppressLint("SetTextI18n")
    private fun updateFromValue(){
        binding.editTextValueFrom.setText(TripManager.getCurrentTripValue().toString())
        binding.editTextValueFrom.isEnabled = false
    }
    private fun rinse(){
        binding.editTextTo.setText("")
        binding.editTextFrom.setText("")
        binding.checkBox.isChecked = false
        binding.editTextValueTo.setText("")
        binding.calendarView.date = Instant.now().toEpochMilli()
        binding.editTextText3.setText("")
        updateFromValue()
    }

    private fun checkIfCreateEnable() {
        val fromValue = 0<binding.editTextValueFrom.length()
        val toValue = 0<binding.editTextValueTo.length()
        var greater = false
        if(fromValue && toValue)
        {
            greater = binding.editTextValueFrom.text.toString().toInt() <=
                    binding.editTextValueTo.text.toString().toInt()
        }

        val from = 0<binding.editTextFrom.length()
        val to = 0<binding.editTextTo.length()

        binding.btnCreate.isEnabled = (fromValue && toValue && from && to && greater)
    }

    private fun createNewPost(){
        val id = TripManager.getNextId()
        val toValue = binding.editTextValueFrom.text.toString().toInt()
        val endValue = binding.editTextValueTo.text.toString().toInt()
        val date = calender.time
        val company = binding.checkBox.isChecked
        val from = binding.editTextFrom.text.toString()
        val to = binding.editTextTo.text.toString()
        val desc = binding.editTextText3.toString()
        val trip = Trip(id,toValue,endValue, date, company, from, to, desc)

        TripManager.addTrip(trip, requireContext())

        updateFromValue()
    }
}