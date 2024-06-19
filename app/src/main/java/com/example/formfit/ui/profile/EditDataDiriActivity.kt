package com.example.formfit.ui.profile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.formfit.R
import com.example.formfit.data.remote.ApiClient
import com.example.formfit.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDataDiriActivity : AppCompatActivity() {

    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var buttonSave: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data_diri)

        radioGroupGender = findViewById(R.id.radioGroupGender)
        radioMale = findViewById(R.id.radioMale)
        radioFemale = findViewById(R.id.radioFemale)
        editTextWeight = findViewById(R.id.editTextWeight)
        editTextHeight = findViewById(R.id.editTextHeight)
        buttonSave = findViewById(R.id.buttonSave)

        apiService = ApiClient.create()

        val userId = intent.getStringExtra("userId") ?: return

        buttonSave.setOnClickListener {
            val gender = if (radioMale.isChecked) "Male" else "Female"
            val weight = editTextWeight.text.toString().toDoubleOrNull()
            val height = editTextHeight.text.toString().toDoubleOrNull()

            if (weight != null && height != null) {
                saveUserData(userId, gender, weight, height)
            } else {
                Toast.makeText(this, "Please enter valid weight and height", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(userId: String, gender: String, weight: Double, height: Double) {
        val userUpdateRequest = UserUpdateRequest(gender, weight, height)
        apiService.updateUserData(userId, userUpdateRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditDataDiriActivity, "Data updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditDataDiriActivity, "Failed to update data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditDataDiriActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
