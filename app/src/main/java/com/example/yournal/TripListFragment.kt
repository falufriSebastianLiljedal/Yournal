package com.example.yournal

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.yournal.databinding.FragmentTripListBinding

class TripListFragment : Fragment() {
    private  var _binding: FragmentTripListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTripListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnListFragmentBot2.setOnClickListener {

        }
        UpdateListBox()

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TripListFragment().apply {
            }
    }


    fun UpdateListBox(){
        var list = TripManager.getStringTripList()

        for (ob in list)
        {
            Log.d("Recycler",ob)
        }

        var myAdapter: ArrayAdapter<*>
        var myListView = binding.tripListView
        myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        myListView.adapter = myAdapter
    }
}