package com.malkinfo.editingrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // Initialize Firebase Auth
        auth = Firebase.auth
        val loginText: TextView = findViewById(R.id.signIn)
        loginText.setOnClickListener {
            val Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        val registerButton: Button = findViewById(R.id.signupBtn)
        registerButton.setOnClickListener {
            performSignUp()
        }
        //lets get email and password from the user

    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.emailEt)
        val passwod = findViewById<EditText>(R.id.passET)

        if(email.text.isEmpty() || passwod.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",
                Toast.LENGTH_SHORT).show()
            return
        }
        val inputEmail = email.text.toString()
        val inputPassword = passwod.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail,inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, let move to the next Activity
                    val intent = Intent(this,ProViewMainActivity::class.java)

                    startActivity(intent)

                    Toast.makeText(baseContext, "Authentication success.",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Error occured ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}