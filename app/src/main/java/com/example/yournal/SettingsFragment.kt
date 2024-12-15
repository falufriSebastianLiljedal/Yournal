package com.example.yournal

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yournal.databinding.FragmentSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class SettingsFragment : Fragment() {

    private  var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnDeleteAll.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete all")
                .setMessage("Are you sure you want to delete all?")
                .setPositiveButton("Yes") { dialog, _ ->
                    TripManager.deleteAll(requireContext())
                    dialog.dismiss() // Close the dialog
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss() // Close the dialog
                }
                .show()

        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {

            }
    }
}