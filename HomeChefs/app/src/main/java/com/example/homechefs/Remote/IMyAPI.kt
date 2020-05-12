package com.example.homechefs.Remote

import com.example.homechefs.model.APIResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IMyAPI {
    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(@Field("username")username:String,@Field("gender")gender:String,@Field("email")email:String,@Field("phone")phone:String,@Field("password")password:String):Call<APIResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(@Field("username")username:String,@Field("password")password:String):Call<APIResponse>


    @FormUrlEncoded
    @POST("update.php")
    fun updateUser(@Field("unique_id")unique_id:String,@Field("username")username:String,@Field("email")email:String,@Field("phone")phone:String):Call<APIResponse>

    @FormUrlEncoded
    @POST("recipe_insert.php")
    fun addRecipe(@Field("unique_id")unique_id:String,@Field("recipe_name")recipe_name:String,@Field("recipe_description")recipe_description:String):Call<APIResponse>

}