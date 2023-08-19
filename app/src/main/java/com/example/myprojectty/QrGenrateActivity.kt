package com.example.myprojectty

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QrGenrateActivity : AppCompatActivity() {
    private lateinit var ivQRcode: ImageView
    private lateinit var etData: EditText
    private var db= Firebase.firestore
    private lateinit var btnGenrateQRCode : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_genrate)

        ivQRcode=findViewById(R.id.ivQRCode)

        btnGenrateQRCode=findViewById(R.id.btnGenrateQRCode)
        btnGenrateQRCode.setOnClickListener {
            // Generate a random string
            val randomString = generateRandomString(10) // Change 10 to the desired length
            val StringQr=randomString.toString()
            val UserMap= hashMapOf(
                "stringQr" to StringQr,

            )
            db.collection("QrString").document("Attendance").set(UserMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "running Qr", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }



            // Display the random string via Toast
            Toast.makeText(this, "Random String: $randomString", Toast.LENGTH_SHORT).show()

            // Generate QR code for the random string
            val writer = QRCodeWriter()
            try {
                val bitMatrix = writer.encode(randomString, BarcodeFormat.QR_CODE, 512, 512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                ivQRcode.setImageBitmap(bmp)
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }





    }
    private fun generateRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}