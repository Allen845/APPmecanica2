package com.uvdstudio.mecanica2.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.uvdstudio.mecanica2.R
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val usuario = findViewById<TextInputEditText>(R.id.emailEt)
        val password = findViewById<TextInputEditText>(R.id.passET)
        val botonLoger = findViewById<Button>(R.id.btnIngresar)

        botonLoger.setOnClickListener {
            val userText = usuario.text.toString().trim()
            val passText = password.text.toString().trim()

            if (userText.isNotEmpty() && passText.isNotEmpty()) {
                loginBdVolley(userText, passText)
            } else {
                Toast.makeText(this, "Escriba el Usuario/Contraseña", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginBdVolley(usuario: String, password: String) {
        val url = "http://10.0.2.2/mecanica/login.php"  // Ajusta la ruta correcta de tu servidor

        val jsonBody = JSONObject()
        jsonBody.put("usuario", usuario)
        jsonBody.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                try {
                    val success = response.getBoolean("success")
                    if (success) {
                        val user = response.getJSONObject("user")
                        val intent = Intent(this, loginActivity::class.java)
                        intent.putExtra("ID", user.getString("id"))
                        intent.putExtra("Nombre", user.getString("nombre"))
                        intent.putExtra("Perfil", user.getString("perfil"))
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this, "Error procesando la respuesta", Toast.LENGTH_LONG).show()
                }
            },
            {
                Toast.makeText(this, "Error de conexión con el servidor", Toast.LENGTH_LONG).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}
