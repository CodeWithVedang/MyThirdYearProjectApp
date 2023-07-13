package com.example.myprojectty

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.AdapterView
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Admin : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var SpinnerForPaper: Spinner
    lateinit var SpinnerForSubject: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var db: FirebaseFirestore

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
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
                    Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                }

                R.id.FeedbackL-> {
                    val openUrl= Intent(Intent.ACTION_VIEW)
                    openUrl.data= Uri.parse("https://forms.gle/ahAVADMCKFuetYqx6")
                    startActivity(openUrl)
                    Toast.makeText(applicationContext, "Opening...", Toast.LENGTH_SHORT).show()

                }
                R.id.shareL-> {

                    Toast.makeText(applicationContext, "Sharing...", Toast.LENGTH_SHORT).show()
                }
                R.id.AttendanceL->{
                    Attendance()
                    Toast.makeText(this, "Opening Attendance", Toast.LENGTH_SHORT).show()
                }
                R.id.LogoutL-> {
                    LogOutUser()
                    Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show()
                }
                R.id.Admin-> {
                    Admin()
                    Toast.makeText(this, "Already In !!", Toast.LENGTH_SHORT).show()
                }
                R.id.DownloadNow-> {
                    val openDownloadurl=Intent(android.content.Intent.ACTION_VIEW)
                    openDownloadurl.data= Uri.parse("https://download940.mediafire.com/7rz3q1xqxjngllQvjjUE3tL6q1vUOeFAfpdsyAYTAdsDGrg6Pt2M3wt_jjbS3TETmVP4w8ZUrYJwRJlFMq2ZRDJLtq5l9JUZpgIDmDiKcajwcJECqNY1qsG1hznf58OhPdKpzExaeHgtnGgFy3uoC0R7fI-RsejninW8e-_YOrLpBg/84g0a17f7pc3cq0/app-debug.apk")
                    startActivity(openDownloadurl)
                    Toast.makeText(this, "Redirecting", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


        SpinnerForSubject=findViewById(R.id.spinnerOfadminSubject)

        SpinnerForPaper=findViewById(R.id.spinnerOfadminPaper)
        val paper= arrayOf("Paper1","Paper2","Paper3","Paper4","Paper5","Paper6","Paper7","Paper8","Paper9","Paper10","Paper11","Paper12","Paper13","Paper14","Paper15","Paper16","Paper17","Paper18","Paper19","Paper20")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, paper)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerForPaper.adapter = adapter1

        val classssss= arrayOf("FY","SY","TY")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classssss)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerForSubject.adapter = adapter




        var SelectedPaper:String=""
        var SelectedClass:String=""


        SpinnerForPaper.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SelectedPaper=SpinnerForPaper.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



        SpinnerForSubject.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SelectedClass=SpinnerForSubject.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val AddClass=findViewById<EditText>(R.id.classAdmin)
        val AddSubject=findViewById<EditText>(R.id.subjectAdmin)

        val updateSubject=findViewById<Button>(R.id.updateSubject)
        val updateClass=findViewById<Button>(R.id.updateClass)

        db = FirebaseFirestore.getInstance()
        updateClass.setOnClickListener {

           val InputClass=AddClass.text.toString().trim()
            val HashClass= mapOf(
                SelectedClass to InputClass
            )

            db.collection("Class").document("ClassName").update(HashClass)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully updated : $InputClass", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update : $InputClass", Toast.LENGTH_SHORT).show()
                }
        }



        updateSubject.setOnClickListener {
            val InputSubject=AddSubject.text.toString().trim()
            val HashSubject= mapOf(
               SelectedPaper to InputSubject

            )
            db.collection("subject").document("subject").update(HashSubject)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully updated : $InputSubject", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update : $InputSubject", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(this, "You Are Already Here ! ", Toast.LENGTH_SHORT).show()
    }

    private fun Admin() {
        val intent= Intent(this,AdminAcitivty::class.java)
        startActivity(intent)
    }
}
