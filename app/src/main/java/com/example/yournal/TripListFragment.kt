package com.example.yournal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yournal.databinding.FragmentTripListBinding
import kotlinx.coroutines.NonDisposableHandle.parent

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

        UpdateListBox()

        Log.d("Recycler","Bottom on create view",)
        return inflater.inflate(R.layout.fragment_trip_list, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TripListFragment().apply {

            }
    }


    fun UpdateListBox(){
        var list = TripManager.getStringTripList()
        val customAdapter = TripAdapter(list)

        for (ob in list)
        {
            Log.d("Recycler",ob)
        }


        binding.tripRec.layoutManager = LinearLayoutManager(this.context)
        binding.tripRec.adapter = customAdapter
        binding.tripRec.hasPendingAdapterUpdates()
        Log.d("Recycler","Adapter added",)


    }
}

class TripAdapter(private val dataSet: List<String>):
    RecyclerView.Adapter<TripAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView
        init{
            Log.d("Viewholder","init viewholder",)
            textView = itemView.findViewById(R.id.singleTripListTextView)
        }
    }
        //https://developer.android.com/develop/ui/views/layout/recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.ViewHolder {
        Log.d("Tripadapter","Oncreateviewholder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_trip_list, parent, false)
        Log.d("Tripadapter","Creating layout")

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("TripAdapter","onBindViewHolder",)
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size


}



