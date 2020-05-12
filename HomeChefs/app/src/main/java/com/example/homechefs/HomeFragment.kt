package com.example.homechefs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.homechefs.Common.Common
import com.example.homechefs.Remote.IMyAPI
import com.example.homechefs.model.APIResponse
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    internal lateinit var mService:IMyAPI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false) }


    override fun onResume() {
        super.onResume()


        var sys_anim1: Animation? = null
        sys_anim1 = AnimationUtils.loadAnimation(activity!!, R.anim.rotate)

        sys_anim1.setFillAfter(true)





        imageView.setOnClickListener {
            val options =
                arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder =
                AlertDialog.Builder(activity!!)
            builder.setTitle("Choose your profile picture")
            builder.setItems(
                options
            ) { dialog, item ->
                if (options[item] == "Take Photo") {
                    val takePicture =
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                } else if (options[item] == "Choose from Gallery") {
                    val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(pickPhoto, 1)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }




        mService = Common.api

        val sharedpreferences = activity!!.getSharedPreferences("USER", Context.MODE_PRIVATE)
        val unique_id = sharedpreferences.getString("UID","")

        fab.setOnClickListener {

            fab.startAnimation(sys_anim1)

            addNewRecipe(unique_id.toString(),
            txt_recipe_name.text.toString(),
            txt_recipe_description.text.toString())

        }
    }

    private fun addNewRecipe(unique_id:String,recipe_name:String,recipe_description:String){
        mService.addRecipe(unique_id,recipe_name, recipe_description).enqueue(object :
            Callback<APIResponse> {
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(activity,t!!.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response!!.body()!!.error){
                    Toast.makeText(activity,response!!.body()!!.error_msg, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity,"Recipe Added Successfully"+response.body()!!.uid,
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
