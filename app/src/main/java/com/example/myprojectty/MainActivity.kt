package com.example.myprojectty

import android.app.Activity
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
import com.google.zxing.integration.android.IntentIntegrator
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var ClassSpinner: Spinner
    lateinit var Spinnerrrr: Spinner
    lateinit var classesssssss: String
    lateinit var Subjectttt: String
    private lateinit var db: FirebaseFirestore
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient  ///lcoation///
    lateinit var adapter: ArrayAdapter<String>
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var DateView: TextView
    private lateinit var calendar: Calendar
    private lateinit var auth: FirebaseAuth
    private lateinit var rollll: FirebaseAuth
    lateinit var ResultText:String
    lateinit var returnedText:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val navView: NavigationView = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.mainDrawer)
        val toolbar = findViewById<Toolbar>(R.id.appBar)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.HomeL -> {
                    Home()
                    Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                }

                R.id.FeedbackL -> {
                    val openUrl = Intent(android.content.Intent.ACTION_VIEW)
                    openUrl.data = Uri.parse("https://forms.gle/ahAVADMCKFuetYqx6")
                    startActivity(openUrl)
                    Toast.makeText(applicationContext, "Opening...", Toast.LENGTH_SHORT).show()
                }
                R.id.shareL -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "\"\uD83D\uDCE2 Hey there! \uD83D\uDCDA\uD83D\uDCDD\n" +
                                "\n" +
                                "I'm excited to share a fantastic new app with you! \uD83C\uDF89\uD83D\uDCF1 It's called the Student Attendance App, and it's designed to simplify attendance management for students. \uD83D\uDCCA✏️\n" +
                                "\n" +
                                "To get started, simply click the download button below and join the attendance revolution! ⬇️\uD83D\uDCBB\n" +
                                "\n" +
                                "\uD83D\uDCE5 [Download Now] \uD83D\uDCE5\n" +
                                " \uD83D\uDC49 bit.ly/getourappnow-std\n" +
                                "\n" +
                                "With this app, you'll be able to effortlessly track your attendance, stay organized, and never miss a class again! \uD83D\uDCC5\uD83D\uDC69\u200D\uD83C\uDFEB✅\n" +
                                "\n" +
                                "Here are some awesome features it offers:\n" +
                                "\n" +
                                "\uD83D\uDCCC Easy attendance management: Mark your presence in a few taps.\n" +
                                "\uD83D\uDCCC Timely reminders: Receive notifications for upcoming classes.\n" +
                                "\uD83D\uDCCC Detailed insights: Get attendance reports and monitor your progress.\n" +
                                "\uD83D\uDCCC Customizable settings: Personalize the app to suit your needs.\n" +
                                "\uD83D\uDCCC User-friendly interface: Enjoy a seamless and intuitive experience.\n" +
                                "\n" +
                                "So, why wait? Grab your phone and download the Student Attendance App now! \uD83D\uDD25\uD83D\uDCF2\n" +
                                "\n" +
                                "Share this message with your friends and classmates, and let's revolutionize the way we manage attendance together! \uD83E\uDD1D✨\n" +
                                "\n" +
                                "If you have any questions or need assistance, feel free to reach out. Enjoy the app and happy attending! \uD83D\uDE0A\uD83C\uDF93\n" +
                                "\n" +
                                "Best regards,\n" +
                                "Vedang Shelatkar(GJC BSC.TYCS)\""
                    )
                    intent.type = "text/plain"
                    startActivity(Intent.createChooser(intent, "Share this App to"))

                    Toast.makeText(applicationContext, "Sharing...", Toast.LENGTH_SHORT).show()
                }
                R.id.AttendanceL -> {
                    Attendance()
                    Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show()
                }
                R.id.LogoutL -> {
                    LogOutUser()
                    Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show()
                }
                R.id.Admin -> {
                    Admin()
                    Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show()
                }
                R.id.DownloadNow -> {
                    val openDownloadurl = Intent(android.content.Intent.ACTION_VIEW)
                    openDownloadurl.data =
                        Uri.parse("https://drive.google.com/drive/folders/1rW_whvH33uvMVbKKkbVgDBjkwx-8ZejW")
                    startActivity(openDownloadurl)
                    Toast.makeText(this, "Redirecting", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        val Btn = findViewById<Button>(R.id.Btn)
        Btn.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
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
        ClassSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                classDatat = ClassSpinner.selectedItem.toString()
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

        var SubjectSelected: String = ""
        Spinnerrrr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SubjectSelected = Spinnerrrr.selectedItem.toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //get current user email
        val Cuser = auth.currentUser?.email.toString()
        Toast.makeText(this, "Logged in as $Cuser", Toast.LENGTH_LONG).show()


//https://youtu.be/w9lmmx8aYp4
        val calendar = Calendar.getInstance().time
        val dateFormat = DateFormat.getDateInstance(DateFormat.LONG).format(calendar)
        val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

        val date = dateFormat.toString().trim()
        val time = timeFormat.toString()
        val DataHash = hashMapOf(
            "Date" to date,
            "Time" to time,

            )
        val docRef = db.collection("QrString").document("Attendance")
        docRef.get()
            .addOnSuccessListener { document->
                if(document!=null){
                    returnedText=document.data!!["stringQr"].toString()
                }
            }
        val SubmitBtn = findViewById<Button>(R.id.SubMitBtn)
        SubmitBtn.setOnClickListener {
            if (ResultText==returnedText){
                val Email = intent.getStringExtra("EmailLgn").toString()
                db.collection("Attendance").document(classDatat).collection(SubjectSelected)
                    .document(Cuser).collection(Cuser).document(date).set(DataHash)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "Date :$date & Time :$time", Toast.LENGTH_LONG).show()
                    }

            }else{
                Toast.makeText(this, "Failed due to some error", Toast.LENGTH_SHORT).show()
            }

        }







        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
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

    private var backPressedCount = 0
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.mainDrawer)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (backPressedCount == 0) {
                showExitConfirmationDialog()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Confirmation")
        builder.setMessage("Are you sure you want to exit ?")
        builder.setPositiveButton("Yes") { _, _ ->
            finishAffinity()
        }
        builder.setNegativeButton("NO") { dialog, _ ->
            dialog.dismiss()
            backPressedCount = 0
        }
        val dialog = builder.create()
        dialog.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    ResultText=result.contents.toString()
                    Toast.makeText(this, "$ResultText", Toast.LENGTH_SHORT).show()

                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    private fun LogOutUser() {
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun Home() {
        Toast.makeText(this, "Already in", Toast.LENGTH_SHORT).show()
    }

    private fun Attendance() {
        val intent = Intent(this, Attendance::class.java)
        startActivity(intent)
    }

    private fun Admin() {
        val intent = Intent(this, AdminAcitivty::class.java)
        startActivity(intent)
    }
}