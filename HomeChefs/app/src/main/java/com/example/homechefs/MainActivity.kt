package com.example.homechefs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homechefs.Common.Common
import com.example.homechefs.Remote.IMyAPI
import com.example.homechefs.model.APIResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    internal lateinit var mService:IMyAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btn_register.setOnClickListener{
            val intent = Intent(this,Registration::class.java)
            startActivity(intent)
        }
        //Initialize service
        mService = Common.api

        btn_login.setOnClickListener { authenticateUser(textfield_username.text.toString(),textfield_password.text.toString()) }

    }

    private fun authenticateUser(username:String,password:String){
        mService.loginUser(username, password).enqueue(object : Callback<APIResponse>{
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,t!!.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response!!.body()!!.error){
                    Toast.makeText(this@MainActivity,response!!.body()!!.error_msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    val sharedpreferences = getSharedPreferences("USER", Context.MODE_PRIVATE)
                    sharedpreferences.edit().putString("UID",response!!.body()!!.uid.toString()).apply()
                    sharedpreferences.edit().putString("USERNAME",response!!.body()!!.user!!.username).apply()
                    sharedpreferences.edit().putString("EMAIL",response!!.body()!!.user!!.email).apply()
                    sharedpreferences.edit().putString("PHONE", response!!.body()!!.user!!.phone).apply()
                    sharedpreferences.edit().putString("GENDER",response!!.body()!!.user!!.gender).apply()

                    Log.d("====================",response!!.body()!!.user!!.phone)

                    val intent1 = Intent(this@MainActivity,Home::class.java)
                    startActivity(intent1)
                }
            }
        })
    }

}
