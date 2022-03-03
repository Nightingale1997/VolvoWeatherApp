package com.example.volvoweather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.volvoweather.ApiInterface
import com.example.volvoweather.databinding.MainFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.widget.RelativeLayout
import com.example.volvoweather.R


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //Fetch Weather with coordinates of Gothenburg
        viewModel.fetchWeather(57.72, 11.98)
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}