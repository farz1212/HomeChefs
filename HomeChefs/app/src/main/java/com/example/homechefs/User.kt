package com.example.homechefs

class User {
    var id:Int = 0
    var username:String = ""
    var password:String = ""
    var email:String = ""
    var phone:Int = 0

    constructor(username:String,password:String,email:String,phone:Int)
    {
        this.username = username
        this.password = password
        this.email = email
        this.phone = phone
    }

}