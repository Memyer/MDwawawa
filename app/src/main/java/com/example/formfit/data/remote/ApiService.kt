package com.example.formfit.data.remote

import com.example.formfit.ui.login.LoginRequest
import com.example.formfit.ui.login.LoginResponse
import com.example.formfit.ui.profile.UserResponse
import com.example.formfit.ui.profile.UserUpdateRequest
import com.example.formfit.ui.register.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface ApiService {

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest):
            Call<LoginResponse>

    @POST("/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<Void>

    @GET("/users/{id}")
    fun getUserData(@Path("id") id: String): Call<UserResponse>

    @PUT("/users/{id}")
    fun updateUserData(@Path("id") id: String, @Body userUpdateRequest: UserUpdateRequest): Call<Void>

}
