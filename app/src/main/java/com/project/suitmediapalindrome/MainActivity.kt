package com.project.suitmediapalindrome

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textName: EditText
    private lateinit var textPalindrome: EditText
    private lateinit var buttonCheck: Button
    private lateinit var buttonNext: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textName = findViewById(R.id.textName)
        textPalindrome = findViewById(R.id.textPalindrome)
        buttonCheck = findViewById(R.id.buttonCheck)
        buttonNext = findViewById(R.id.buttonNext)

        buttonCheck.setOnClickListener {
            val input = textPalindrome.text.toString()
            if (input.isBlank()) {
                showDialog("Input cannot be empty")
            } else {
                val isPalin = isPalindrome(input)
                showDialog(if (isPalin) "the sentence is palindrome" else "the sentence is not palindrome")
            }
        }
        buttonNext.setOnClickListener {
            val name = textName.text.toString()
            val intent = Intent(this, secondScreen::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun isPalindrome(input: String): Boolean {
        val reversed = input.reversed()
        return input.equals(reversed, ignoreCase = true)

    }
}