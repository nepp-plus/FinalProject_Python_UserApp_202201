package com.neppplus.finalproject_python_userapp_202201

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.finalproject_python_userapp_202201.databinding.ActivitySignUpBinding
import com.neppplus.finalproject_python_userapp_202201.models.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {
    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnSignUp.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputPwRepeat = binding.edtPasswordRepeat.text.toString()
            val inputName = binding.edtName.text.toString()
            val inputPhone = binding.edtPhone.text.toString()

            if (inputEmail.isEmpty()) {
                Toast.makeText(mContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (inputPw.length < 8) {
                Toast.makeText(mContext, "비번은 8글자 이상으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (inputPw != inputPwRepeat) {
                Toast.makeText(mContext, "두 비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputEmail.isEmpty()) {
                Toast.makeText(mContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (inputName.isEmpty()) {
                Toast.makeText(mContext, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (inputPhone.isEmpty()) {
                Toast.makeText(mContext, "연락처를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            apiService.putRequestSignUp(
                inputEmail,
                inputPw,
                inputName,
                inputPhone
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {

                        Toast.makeText(mContext, "회원가입 완료", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        val jsonObj = JSONObject(response.errorBody()!!.string())

                        Toast.makeText(mContext, jsonObj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    Log.d("서버연결실패", t.localizedMessage)
                    Toast.makeText(mContext, "서버 연결 실패", Toast.LENGTH_SHORT).show()
                }

            })



        }


    }

    override fun setValues() {

    }
}