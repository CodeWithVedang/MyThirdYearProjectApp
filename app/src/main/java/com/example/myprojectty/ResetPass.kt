package com.example.myprojectty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPass : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var resetButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var BackBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        emailEditText = findViewById(R.id.Email)
        resetButton = findViewById(R.id.SendLink)
        auth = FirebaseAuth.getInstance()
        resetButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                emailEditText.error = "Please enter your email"
                return@setOnClickListener
            }
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Check Email Link Sent to $email", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "Check Inbox or Spam Folder", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed Due to some Error", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        BackBtn=findViewById(R.id.BackBtn)
        BackBtn.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        }
}