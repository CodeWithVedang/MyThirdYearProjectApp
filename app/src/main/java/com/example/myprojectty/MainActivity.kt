package com.example.myprojectty

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
lateinit var drawerLayout: DrawerLayout
lateinit var ClassSpinner:Spinner
lateinit var Spinnerrrr:Spinner
lateinit var classesssssss:String
lateinit var  Subjectttt:String
private lateinit var db: FirebaseFirestore
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient  ///lcoation///
lateinit var adapter: ArrayAdapter<String>
lateinit var toggle: ActionBarDrawerToggle
    private lateinit var DateView:TextView
    private lateinit var calendar:Calendar
    private lateinit var auth : FirebaseAuth
    private lateinit var rollll: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val navView:NavigationView=findViewById(R.id.nav_view)
        drawerLayout=findViewById(R.id.mainDrawer)
        val toolbar=findViewById<Toolbar>(R.id.appBar)
        toolbar.setNavigationOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }
        toggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
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
                    Admin()
                    Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show()
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


 /* val NameOFUser=findViewById<TextView>(R.id.NameOfuser)

        val emailPass =intent.getStringExtra("EmailLgn").toString()
        NameOFUser.text=emailPass
*/


        //spinnername
        ClassSpinner = findViewById(R.id.spinner)

        db = FirebaseFirestore.getInstance()

        // Get the data from Firestore
        db.collection("Class").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var classss = mutableListOf<String>()    //list
                for (document in task.result) {
                   classss.add(document.get("FY").toString())
                     classss.add(document.get("SY").toString())
                    classss.add(document.get("TY").toString())

                }

                // Create an adapter for the spinner
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, classss)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // Set the adapter on the spinner
                ClassSpinner.adapter = adapter
            }

        }
        var classDatat = ""
        ClassSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                classDatat=ClassSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }






        Spinnerrrr = findViewById(R.id.Spinnerrrr)
        db = FirebaseFirestore.getInstance()

        // Get the data from Firestore
        db.collection("subject").get()
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val subject = mutableListOf<String>()
                for (document in task.result) {
                    subject.add(document.get("Paper1").toString())
                    subject.add(document.get("Paper2").toString())
                    subject.add(document.get("Paper3").toString())
                    subject.add(document.get("Paper4").toString())
                    subject.add(document.get("Paper5").toString())
                    subject.add(document.get("Paper6").toString())
                    subject.add(document.get("Paper7").toString())
                    subject.add(document.get("Paper8").toString())
                    subject.add(document.get("Paper9").toString())
                    subject.add(document.get("Paper10").toString())
                    subject.add(document.get("Paper11").toString())
                    subject.add(document.get("Paper12").toString())
                    subject.add(document.get("Paper13").toString())
                    subject.add(document.get("Paper14").toString())
                    subject.add(document.get("Paper15").toString())
                    subject.add(document.get("Paper16").toString())
                    subject.add(document.get("Paper17").toString())
                    subject.add(document.get("Paper18").toString())
                    subject.add(document.get("Paper19").toString())
                    subject.add(document.get("Paper20").toString())

                }

                // Create an adapter for the spinner
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subject)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // Set the adapter on the spinner
                Spinnerrrr.adapter = adapter
            }
        }

       var SubjectSelected:String=""
        Spinnerrrr.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SubjectSelected=Spinnerrrr.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



//https://youtu.be/w9lmmx8aYp4
        val calendar=Calendar.getInstance().time
        val dateFormat=DateFormat.getDateInstance(DateFormat.LONG).format(calendar)
        val timeFormat=DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

        val date=dateFormat.toString().trim()
        val time=timeFormat.toString()
        val DataHash= hashMapOf(
            "Date" to date,
            "Time" to time,

        )

        val SubmitBtn=findViewById<Button>(R.id.SubMitBtn)
        SubmitBtn.setOnClickListener {

            val Email =intent.getStringExtra("EmailLgn").toString()
            db.collection("Attendance").document(classDatat).collection(SubjectSelected).document(Email).collection(Email).document(date).set(DataHash)
                .addOnSuccessListener {
                    Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Date :$date & Time :$time", Toast.LENGTH_LONG).show()
                }

        }







        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
    }//functions



    /*private fun getLocation() {
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION ),100)
                return

        }
        val locationn=fusedLocationProviderClient.lastLocation
        locationn.addOnSuccessListener {
            if (it!=null){
                it.latitude.toString()
                it.altitude.toString()

                val latitude=it.latitude.toString()
                val altitude=it.altitude.toString()
                Toast.makeText(this, "current latitude:$latitude & altitude $altitude", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    private var backPressedCount=0
    override fun onBackPressed() {
       val drawerLayout:DrawerLayout=findViewById(R.id.mainDrawer)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            if (backPressedCount==0){
                showExitConfirmationDialog()
            }else{
                super.onBackPressed()
            }
        }
    }

    private fun showExitConfirmationDialog() {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Exit Confirmation")
        builder.setMessage("Are you sure you want to exit ?")
        builder.setPositiveButton("Yes"){ _,_->
            finishAffinity()
        }
        builder.setNegativeButton("NO"){dialog,_->
            dialog.dismiss()
            backPressedCount=0
        }
        val dialog=builder.create()
        dialog.show()
    }




    private fun LogOutUser() {
        Firebase.auth.signOut()
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun Home(){
        Toast.makeText(this, "Already in", Toast.LENGTH_SHORT).show()
    }
    private fun Attendance(){
        val intent=Intent(this,Attendance::class.java)
        startActivity(intent)
    }
    private fun Admin() {
        val intent=Intent(this,AdminAcitivty::class.java)
        startActivity(intent)
    }
}