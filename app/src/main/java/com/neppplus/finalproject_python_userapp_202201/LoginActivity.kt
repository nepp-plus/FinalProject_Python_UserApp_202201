package com.neppplus.finalproject_python_userapp_202201

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivityLoginBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            apiList.postRequestLogin(
                inputEmail,
                inputPw,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    val br = response.body()!!
                    val loginUser = br.data.user

                    Toast.makeText(mContext, "${loginUser.name}님, 환영합니다!", Toast.LENGTH_SHORT)
                        .show()


                    val myIntent = Intent(mContext, MainActivity::class.java)
                    startActivity(myIntent)
                    finish()

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

        binding.btnSignUp.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

    }
}