package com.uvdstudio.mecanica2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        var usuario = findViewById<TextInputEditText>(R.id.emailEt)
        var password = findViewById<TextInputEditText>(R.id.passET)
        var boton_loger = findViewById<Button>(R.id.btnIngresar)


        boton_loger.setOnClickListener {
            if(usuario.text.toString()!="" && password.text.toString()!=""){
                login_bd_volley(usuario.text.toString(),password.text.toString())
            }else{
                Toast.makeText(applicationContext,"Ecriba el Usuario/Contraseña", Toast.LENGTH_LONG).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun login_bd_volley(usuario:String, password:String){
        var url = "http://localhost/C:\\XAMPP_NEW\\htdocs"

        var peticion_post = object:StringRequest(Method.POST, url, Response.Listener { response->

            try {
                        var respuesta = JSONArray(response)
                        var valores = respuesta.getJSONObject(0)

                        var intent = Intent(applicationContext, loginActivity::class.java)
                intent.putExtra("ID", valores.get("id").toString())
                intent.putExtra("Nombre", valores.get("nombre").toString())
                intent.putExtra("Valores", valores.get("perfil").toString())

                   startActivity(intent)

            }catch (ex:Exception){
                Toast.makeText(applicationContext,"Usuario/ Contraseña Invalidos.", Toast.LENGTH_LONG).show()
            }

        },Response.ErrorListener { error ->
            Toast.makeText(applicationContext,"Usuario/ Contraseña Invalidos.", Toast.LENGTH_LONG).show()
        })
        {
                override fun getParams():MutableMap<String, String> {

                    var params = HashMap<String, String>()
                    params.put("usuario", usuario)
                    params.put("password", password)
                    return params
                }
        }
        Volley.newRequestQueue(this).add(peticion_post)
    }
}