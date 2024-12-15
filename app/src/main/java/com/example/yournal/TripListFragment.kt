package com.example.yournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.yournal.databinding.FragmentTripListBinding

class TripListFragment : Fragment() {
    private var _binding: FragmentTripListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTripListBinding.inflate(inflater, container, false)
        val view = binding.root

        updateListBox()

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TripListFragment().apply {
            }
    }


    private fun updateListBox() {
        val list = TripManager.getStringTripList()

        val myListView = binding.tripListView
        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        myListView.adapter = myAdapter
        binding.tripListView.post {
            binding.tripListView.setSelection(binding.tripListView.adapter.count - 1)
        }
    }
}