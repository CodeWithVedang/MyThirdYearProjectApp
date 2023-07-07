package com.example.myprojectty

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var RollNumber:EditText
    private var db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= Firebase.auth
        auth = FirebaseAuth.getInstance()
        val SignUptext=findViewById<TextView>(R.id.SignUpText)
        SignUptext.setOnClickListener {
            val intent=Intent(this,SIgnUpActivity::class.java)
            startActivity(intent)
        }
        val LoginBtn=findViewById<Button>(R.id.LoginBtn)
        LoginBtn.setOnClickListener {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            performLogin()


        }

    }




    private fun performLogin() {
            val Email=findViewById<EditText>(R.id.Email)
            val Password=findViewById<EditText>(R.id.pasword)

    if (Email.text.isEmpty()||Password.text.isEmpty()){
        Toast.makeText(this, "Please Enter Fields", Toast.LENGTH_SHORT).show()
        return
}
        val InputEmail=Email.text.toString()
        val InputPass=Password.text.toString()
        auth.signInWithEmailAndPassword(InputEmail,InputPass)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    val intent=Intent(this,MainActivity::class.java).putExtra("EmailLgn",InputEmail)
                    startActivity(intent)
                    Toast.makeText(this, "Successful logged in", Toast.LENGTH_SHORT).show()
                    saveStudentData()


                }
                else{
                    Toast.makeText(this, "Log In Failed! Try Again !", Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show()

            }

    }



    private fun saveStudentData() {
        val Email=findViewById<EditText>(R.id.Email)
        val Password=findViewById<EditText>(R.id.pasword)
        RollNumber=findViewById(R.id.Rollnumber)
        if (Email.text.isEmpty()||Password.text.isEmpty()||RollNumber.text.isEmpty()){
            Toast.makeText(this, "Please Enter Fields", Toast.LENGTH_SHORT).show()
            return
        }
        val InputEmail=Email.text.toString().trim()
        val InputPasword=Password.text.toString().trim()
        val InputRollNumber=RollNumber.text.toString().trim()

        val UserMap= hashMapOf(
            "RollNumber" to InputRollNumber,
            "Email" to InputEmail,
            "Password" to InputPasword
        )

        db.collection("Student").document(InputEmail).set(UserMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
                Email.text.clear()
                Password.text.clear()
                RollNumber.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }


    }

   /* override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser!=null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }*/

}