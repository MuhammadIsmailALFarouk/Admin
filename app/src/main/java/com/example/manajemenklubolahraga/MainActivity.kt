package com.example.manajemenklubolahraga

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var btnBrowse: Button
    private lateinit var btnUpload: Button
    private lateinit var btnSave : Button
    private lateinit var setName: EditText
    private lateinit var setTim : EditText
    private lateinit var statistik : EditText
    private lateinit var jadwal : EditText
    private lateinit var cek : Button

    private var storageRef = Firebase.storage
    private var db = Firebase.firestore

    private lateinit var uri: Uri
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storageRef = FirebaseStorage.getInstance()
        image = findViewById(R.id.reviewImg)
        btnBrowse = findViewById(R.id.btnBrowse)
        btnUpload = findViewById(R.id.btnUpload)
        btnSave = findViewById(R.id.btnSubmit)

        setName = findViewById(R.id.setName)
        setTim = findViewById(R.id.setTim)
        statistik = findViewById(R.id.statistik)
        jadwal = findViewById(R.id.jadwal)
        cek = findViewById(R.id.buttonCek)

        


        btnSave.setOnClickListener{
            val etName = setName.text.toString().trim()
            val etTim = setTim.text.toString().trim()
            val etStatistik = statistik.text.toString().trim()
            val etJadwal = jadwal.text.toString().trim()

            val userMap = hashMapOf(
                "namePemain" to etName,
                "nameTim" to etTim,
                "statistikPemain" to etStatistik,
                "jadwal" to etJadwal
            )
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("user").document().set(userMap)
                .addOnCompleteListener {
                    Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
                    setName.text.clear()
                    setTim.text.clear()
                    statistik.text.clear()
                    jadwal.text.clear()

                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
        }


        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                image.setImageURI(it)
                uri = it!!
            })

        btnBrowse.setOnClickListener {
            galleryImage.launch("image/*")
        }

        btnUpload.setOnClickListener {
            storageRef.getReference("images").child("${System.currentTimeMillis()}.jpg")
                .putFile(uri)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val userId = FirebaseAuth.getInstance().currentUser!!.uid
                            val mapImage = mapOf(
                                "url" to it.toString()
                            )
                            val databaseReference = FirebaseDatabase.getInstance().getReference("userImages")
                            databaseReference.child(userId).setValue(mapImage)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{error ->
                                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                                }
                        }

                }

        }
        cek.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

        }

    }
}