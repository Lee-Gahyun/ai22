package com.example.aifriend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aifriend.R
import com.example.aifriend.adapter.FavoriteAdapter
import com.example.aifriend.adapter.FavoriteItem
import com.example.aifriend.databinding.FragmentTab2Binding

class Tab2 : Fragment() {

    private lateinit var binding: FragmentTab2Binding

    private val favoriteSet = mutableSetOf<FavoriteItem>()

    private val favoriteAdapter = FavoriteAdapter(onItemClick = {
        val intent = Intent(requireContext(), FavoriteActivity::class.java)
        intent.putExtra("favorite", it.name)
        startActivity(intent)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTab2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tab2Recyclerview.adapter = favoriteAdapter

        MyApplication.db.collection("user").document(MyApplication.email!!).collection("favorite")
            .document("AI친구").get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val item = it.result.data as Map<String, String>?
                    favoriteSet.add(FavoriteItem(item?.get("favorite").orEmpty()))
                    favoriteAdapter.addAll(favoriteSet.toList())
                }
            }
    }
}