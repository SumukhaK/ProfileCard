package com.ksa.profilecard

data class UserProfile constructor(val name:String, val status:Boolean, val drawableId:Int)

val userProfileList = arrayListOf(
    UserProfile(name = "John Doe", status = true,R.drawable.profile2),
    UserProfile(name = "Hanna Baker", status = false,R.drawable.profile3),
    UserProfile(name = "Abraham Lincoln", status = true,R.drawable.profile4)
)