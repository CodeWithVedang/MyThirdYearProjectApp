package com.example.myprojectty

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AttendanceRecordActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var spinner: Spinner
    lateinit var adapter: ArrayAdapter<String>
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var DateView: TextView
    private lateinit var calendar: Calendar
    private lateinit var auth : FirebaseAuth
    private lateinit var db: FirebaseFirestore
    lateinit var ClassSpinner: Spinner
    lateinit var Spinnerrrr: Spinner



    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<fetchdataclassuser>
    private  var dbb=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_record)
        auth = FirebaseAuth.getInstance()
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
                    Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        val RollNumberr =intent.getStringExtra("rollnumber").toString()
        val classs =intent.getStringExtra("class").toString()
        val subject =intent.getStringExtra("subject").toString()
        val textroll=findViewById<TextView>(R.id.RolllText)
        textroll.text="Hey "+RollNumberr+" !!"


        recyclerView=findViewById(R.id.recyclerview)

        userList = arrayListOf()
        dbb= FirebaseFirestore.getInstance()
        dbb.collection("Attendance").document(classs).collection(subject).document(RollNumberr).collection(RollNumberr).get()
            .addOnSuccessListener {
              if(!it.isEmpty){
                  for (data in it.documents){
                      val user: fetchdataclassuser? = data.toObject(fetchdataclassuser::class.java)
                      if (user != null ) {
                          userList.add(user)
                      }
                  }
                  recyclerView.adapter=MyAdapter(userList)
                  recyclerView.layoutManager=LinearLayoutManager(this)

              }
            }
            .addOnFailureListener {
                Toast.makeText(this,"failed to retrive !"+it.toString() , Toast.LENGTH_SHORT).show()
            }





    }




    ///functions
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
        val intent=Intent(this,AdminAcitivty::class.java)
        startActivity(intent)
    }
}