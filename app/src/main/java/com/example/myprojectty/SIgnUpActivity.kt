package com.example.myprojectty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SIgnUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth= Firebase.auth
        val SignUpBtn=findViewById<Button>(R.id.SignUpBtn)
        SignUpBtn.setOnClickListener {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            performSignUp()
        }
    }

    private fun performSignUp() {
        val Email = findViewById<EditText>(R.id.Email)
        val CreatePassWord = findViewById<EditText>(R.id.CreatePassWord)
        val ConfirmPassword = findViewById<EditText>(R.id.ConfirmPassword)
        val RollNumber = findViewById<EditText>(R.id.RollNumber)
        if (Email.text.isEmpty() || CreatePassWord.text.isEmpty() || ConfirmPassword.text.isEmpty() || RollNumber.text.isEmpty()) {
            Toast.makeText(this, "Enter All Fields", Toast.LENGTH_SHORT).show()
            return
        }
        val inputEmail = Email.text.toString()
        val CreatePasswordTemp = CreatePassWord.text.toString()
        val inputPass = ConfirmPassword.text.toString()
        if (inputPass == CreatePasswordTemp) {
            auth.createUserWithEmailAndPassword(inputEmail, inputPass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to Log In Try Again !!", Toast.LENGTH_SHORT)
                            .show()
                    }


                }
                .addOnFailureListener {
                    Toast.makeText(this, "An ERROR occurred! Please Try Again", Toast.LENGTH_SHORT)
                        .show()
                }


        }
    }
}