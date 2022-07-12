package com.example.aifriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aifriend.R
import com.example.aifriend.databinding.FragmentTab2Binding

class Tab2 : Fragment() {

    lateinit var binding: FragmentTab2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTab2Binding.inflate(inflater, container, false)
        setUpView()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpView(){

    }
}