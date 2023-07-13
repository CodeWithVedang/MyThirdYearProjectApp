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
                    val intent=Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,
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
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share this App to"))
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
                    openDownloadurl.data= Uri.parse("https://drive.google.com/drive/folders/1rW_whvH33uvMVbKKkbVgDBjkwx-8ZejW")
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
