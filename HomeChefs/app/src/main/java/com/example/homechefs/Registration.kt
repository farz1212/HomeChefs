package com.example.homechefs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.RemoteViewsService
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homechefs.Common.Common
import com.example.homechefs.Remote.IMyAPI
import com.example.homechefs.model.APIResponse
import kotlinx.android.synthetic.main.activity_main.textfield_password
import kotlinx.android.synthetic.main.activity_main.textfield_username
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {


    internal lateinit var mService: IMyAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mService = Common.api

        val genders = arrayOf<String?>(
            "Male", "Female", "Other"
        )

        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            applicationContext, android.R.layout.simple_list_item_1, genders
        )

        adapter.setDropDownViewResource(R.layout.dropdownlayout)
        spinner2.adapter = adapter

        backto_login.setOnClickListener {finish()
        }

        btn_register.setOnClickListener {
            createNewUser(textfield_reg_username.text.toString(),
                spinner2.selectedItem.toString(),
                textfield_email.text.toString(),
                textfield_phone.text.toString(),
                textfield_reg_password.text.toString())
        }



    }

    private fun createNewUser(username:String,gender:String,email:String,phone:String,password:String){
        mService.registerUser(username,gender, email, phone, password).enqueue(object : Callback<APIResponse>{
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(this@Registration,t!!.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response!!.body()!!.error){
                    Toast.makeText(this@Registration,response!!.body()!!.error_msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@Registration,"Registeration Successful"+response.body()!!.uid,Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
    }
}
