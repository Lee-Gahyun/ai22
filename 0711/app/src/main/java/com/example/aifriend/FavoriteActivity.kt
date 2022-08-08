package com.example.aifriend

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.aifriend.adapter.BoardAdapter
import com.example.aifriend.databinding.ActivityFavoriteBinding
import java.text.SimpleDateFormat
import java.util.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val boardAdapter = BoardAdapter { clickBoardItem ->

    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 2000) {
                intent.getStringExtra("favorite")?.let {
                    getBoardData(it)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra("favorite").orEmpty()

        binding.rvBoard.adapter = boardAdapter

        binding.fab.setOnClickListener {
            startForResult.launch(
                Intent(this@FavoriteActivity, AddBoardActivity::class.java).apply {
                    putExtra("favorite", intent.getStringExtra("favorite"))
                }
            )
        }

        intent.getStringExtra("favorite")?.let {
            getBoardData(it)
        }
    }



    private fun getBoardData(favorite: String) {
        MyApplication.db.collection("Board").document(favorite).get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    val getResult: ArrayList<HashMap<String, String>>? =
                        it.result.get("list") as ArrayList<HashMap<String, String>>?
                    val toResultList = getResult?.map { it.toBoardData() }
                    if (!toResultList.isNullOrEmpty()) {
                        boardAdapter.addAll(toResultList)
                    }
                } else {
                    createBoardCollect(favorite)
                }
            }
        }
    }

    private fun createBoardCollect(favorite: String) {
        MyApplication.db.collection("Board").document(favorite).set(emptyMap<String, BoardData>())
    }

}

data class BoardData(
    val title: String,
    val content: String,
    val name: String,
    val time: String = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time)
) {
    fun translateYear(): String {
        return "${time.substring(0, 4)}/${time.substring(4, 6)}/${time.substring(6, 8)}"
    }
}


fun HashMap<String, String>.toBoardData(): BoardData =
    BoardData(
        title = getValue("title"),
        content = getValue("content"),
        name = getValue("name"),
        time = getValue("time")
    )
