package com.project.suitmediapalindrome

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class secondScreen : AppCompatActivity() {

    private lateinit var selectedUserName: TextView
    private lateinit var name: TextView

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedName = result.data?.getStringExtra("selectedUserName")
            selectedUserName.text = selectedName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        selectedUserName = findViewById(R.id.selectedName)
        name = findViewById(R.id.name)
        val button = findViewById<com.google.android.material.button.MaterialButton>(R.id.buttonChooseUser)

        val initalName = intent.getStringExtra("name").takeIf { !it.isNullOrBlank() } ?: "John Doe"
        name.text = initalName

        button.setOnClickListener {
            val intent = Intent(this, thirdScreen::class.java)
            launcher.launch(intent)
        }

        findViewById<ImageView>(R.id.imageViewBack).setOnClickListener {
            finish()
        }
    }
}