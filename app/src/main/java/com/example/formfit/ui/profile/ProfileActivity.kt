package com.example.formfit.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.formfit.R
import com.example.formfit.data.remote.ApiClient
import com.example.formfit.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var greetingText: TextView
    private lateinit var usernameText: TextView
    private lateinit var genderText: TextView
    private lateinit var weightText: TextView
    private lateinit var heightText: TextView
    private lateinit var buttonEditData: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        greetingText = findViewById(R.id.greetingText)
        usernameText = findViewById(R.id.usernameText)
        genderText = findViewById(R.id.genderText)
        weightText = findViewById(R.id.weightText)
        heightText = findViewById(R.id.heightText)
        buttonEditData = findViewById(R.id.buttonEditData)

        apiService = ApiClient.create()

        val userId = "userId_from_registration" // Retrieve this from your secure storage
        getUserData(userId)

        buttonEditData.setOnClickListener {
            val intent = Intent(this, EditDataDiriActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }

    private fun getUserData(userId: String) {
        apiService.getUserData(userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    usernameText.text = user?.name
                    genderText.text = user?.gender
                    weightText.text = "${user?.weight} kg"
                    heightText.text = "${user?.height} cm"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
