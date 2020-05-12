package com.example.homechefs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homechefs.Common.Common
import com.example.homechefs.Remote.IMyAPI
import com.example.homechefs.model.APIResponse
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    internal lateinit var mService: IMyAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onResume() {

        mService = Common.api

        super.onResume()
        val sharedpreferences = activity!!.getSharedPreferences("USER", Context.MODE_PRIVATE)
        val unique_id = sharedpreferences.getString("UID","")
        val username = sharedpreferences.getString("USERNAME","")
        val email = sharedpreferences.getString("EMAIL","")
        val phone = sharedpreferences.getString("PHONE","")
        val gender = sharedpreferences.getString("GENDER","")

        Log.d("====================",phone)

        textfield_pfrag_username.setText(username)
        textfield_pfrag_email.setText(email)
        textfield_pfrag_phone.setText(phone)

        btn_update.setOnClickListener {

            updateUserInfo(
                unique_id.toString(),
            textfield_pfrag_username.text.toString(),
            textfield_pfrag_email.text.toString(),
            textfield_pfrag_phone.text.toString())
        }




    }



    private fun updateUserInfo(unique_id:String,username:String,email:String,phone:String){
        mService.updateUser(unique_id,username, email, phone).enqueue(object :
            Callback<APIResponse> {
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(activity,t!!.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response!!.body()!!.error){
                    Toast.makeText(activity,response!!.body()!!.error_msg, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity,"Updated Successfully"+response.body()!!.uid,
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}
