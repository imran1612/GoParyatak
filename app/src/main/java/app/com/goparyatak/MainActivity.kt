package app.com.goparyatak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var loginBtn:Button
    lateinit var registerBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        loginBtn = findViewById(R.id.btn_login)
        registerBtn = findViewById(R.id.btn_register)
        /*loginBtn.setBackgroundColor(resources.getColor( R.color.darkgreen))
        registerBtn.setBackgroundColor(resources.getColor( R.color.darkgreen))*/

        loginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Do some work here
                val intent = Intent(applicationContext,LoginActivity::class.java)
                startActivity(intent)
            }

        })

        registerBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Do some work here
                val intent = Intent(applicationContext,RegisterActivity::class.java)
                startActivity(intent)
            }

        })


    }
}