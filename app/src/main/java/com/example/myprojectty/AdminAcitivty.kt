package com.example.myprojectty

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class AdminAcitivty : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var spinner: Spinner
    lateinit var adapter: ArrayAdapter<String>
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var DateView: TextView
    private lateinit var calendar: Calendar
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_acitivty)
        val navView: NavigationView =findViewById(R.id.nav_view)
        drawerLayout=findViewById(R.id.mainDrawer)
        val toolbar=findViewById<Toolbar>(R.id.appBar)
        toolbar.setNavigationOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.HomeL-> {
                    Home()
                    Toast.makeText(applicationContext, "Opening...", Toast.LENGTH_SHORT).show()
                }

                R.id.FeedbackL-> {
                    val openUrl=Intent(android.content.Intent.ACTION_VIEW)
                    openUrl.data= Uri.parse("https://forms.gle/ahAVADMCKFuetYqx6")
                    startActivity(openUrl)
                    Toast.makeText(applicationContext, "Opening...", Toast.LENGTH_SHORT).show()
                }
                R.id.shareL-> {

                    Toast.makeText(applicationContext, "Sharing...", Toast.LENGTH_SHORT).show()
                }
                R.id.AttendanceL->{
                    Attendance()
                    Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show()
                }
                R.id.LogoutL-> {
                    LogOutUser()
                    Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show()
                }
                R.id.Admin-> {

                    Toast.makeText(this, "Already In !!", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        val Email=findViewById<EditText>(R.id.EmailAdmin)
        val password=findViewById<EditText>(R.id.PasswordAdmin)
        val Buttn=findViewById<Button>(R.id.buttonAdmin)

        val admin1="test"
        val pass1="test"
        Buttn.setOnClickListener {

                Toast.makeText(this, "working", Toast.LENGTH_SHORT).show()
                val emailInput=Email.text.toString()
                val passInput=password.text.toString()
                if(emailInput==admin1||passInput==pass1){
                    val intenntt=Intent(this,Admin::class.java)
                    startActivity(intenntt)
                }else{
                    Toast.makeText(this, "wrong credentials", Toast.LENGTH_SHORT).show()
                }



        }

    }//functions
    private fun LogOutUser() {
        Firebase.auth.signOut()
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun Home(){
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    private fun Attendance(){
        val intent=Intent(this,Attendance::class.java)
        startActivity(intent)
    }
}