package com.example.aifriend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.aifriend.databinding.ActivityJoinBinding
import com.example.aifriend.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding
    lateinit var binding2: ActivityJoinBinding
    //뒤로가기 종료
    var mBackWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding2 = ActivityJoinBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //회원가입 버튼 누르면 회원가입하는 창으로 이동, 내용 입력 후 버튼 누르면 메일 전송
        //내용 입력 유무 확인하는 기능 추가하기
        binding.joinBtn.setOnClickListener {
            setContentView(binding2.root)
            binding2.joinBtn.setOnClickListener {
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
                val email: String = binding2.idEditView.text.toString()
                val password: String = binding2.pwEditView.text.toString()

                MyApplication.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding2.idEditView.text.clear()
                        binding2.pwEditView.text.clear()
                        if (task.isSuccessful) {
                            MyApplication.auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    if(sendTask.isSuccessful){
                                        Toast.makeText(baseContext, "회원가입에 성공하였습니다. 전송된 메일을 확인해 주세요.",
                                            Toast.LENGTH_SHORT).show()
                                        setContentView(binding.root) //회원가입 후 로그인 화면으로 이동
                                    }else {
                                        Toast.makeText(baseContext, "메일 전송 실패",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(baseContext, "회원가입 실패",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        //로그인
        binding.loginBtn.setOnClickListener {
            val email: String = binding.idEditView.text.toString()
            val password: String = binding.pwEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.idEditView.text.clear()
                    binding.pwEditView.text.clear()
                    if (task.isSuccessful) {
                        if(MyApplication.checkAuth()){ //로그인 성공
                            MyApplication.email=email
                            finish()
                        }else {
                            //발송된 메일로 인증 확인을 안한경우
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.",
                                Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(baseContext, "로그인 실패",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
    //뒤로가기로 앱 종료
    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기를 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finishAffinity()
        }
    }

}