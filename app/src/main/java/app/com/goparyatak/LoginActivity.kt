package app.com.goparyatak

import LoginModel
import LoginResponse
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import app.com.goparyatak.Retrofit.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
lateinit var btn:Button
lateinit var mobileNumber:EditText
lateinit var password:EditText
lateinit var helpText:TextView
    var session: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login)
        session = SessionManager(applicationContext)
        btn = findViewById(R.id.btn_login)
        mobileNumber = findViewById(R.id.mobile_no)
        password = findViewById(R.id.password)
helpText = findViewById(R.id.helpText)
        TooltipCompat.setTooltipText(helpText, "Log in with your registered mobile number")
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Do some work here
                rawJSON()

            }

        })
    }
    fun rawJSON() {

        // on below line we are creating a retrofit
        // builder and passing our base url
        // on below line we are creating a retrofit
        // builder and passing our base url
        val retrofit = Retrofit.Builder()
            .baseUrl("http://swarajitservices.co.in/goparyatak/Apiv1/") // as we are sending data in json format so
            // we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create()) // at last we are building our retrofit builder.
            .build() // below line is to create an instance for our retrofit api class.
        // below line is to create an instance for our retrofit api class.
        val retrofitAPI: APIService = retrofit.create(APIService::class.java)

        var moNumber = mobileNumber.text
        var pass = password.text
        // passing data from our text fields to our modal class.

        // passing data from our text fields to our modal class.
        val modal = LoginModel(moNumber.toString(), pass.toString(),"500","7cffaaaf6b7d5fcfc6dbfb020c017d1a")

        // calling a method to create a post and passing our modal class.

        // calling a method to create a post and passing our modal class.
        val call: Call<LoginResponse?>? = retrofitAPI.Login(modal)

        // on below line we are executing our method.

        // on below line we are executing our method.
        call?.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>?,
                response: Response<LoginResponse?>
            ) { // this method is called when we get response from our api.


                // we are getting response from our body
                // and passing it to our modal class.
                val responseFromAPI: LoginResponse? = response.body()
                Log.d("DashboardResponse", "" + responseFromAPI)
                if (responseFromAPI?.status?.equals("true") == true) {

                    // Creating user login session
                    // For testing i am stroing name, email as follow
                    // Use user real data
                    session?.createLoginSession(
                        responseFromAPI?.data?.name,
                        responseFromAPI?.data?.userid
                    )

                    val intent = Intent(applicationContext, DashBoardActivity::class.java)
                    intent.putExtra("customer_id", responseFromAPI?.data?.userid)
                    intent.putExtra("customer_name", responseFromAPI?.data?.name)
                    startActivity(intent) // on below line we are getting our data from modal class and adding it to our string.
                    /*val responseString = """
             Response Code : ${response.code().toString()}
             Name : ${responseFromAPI.getName().toString()}
             Job : ${responseFromAPI.getJob()}
             """.trimIndent()*/

                    // below line we are setting our
                    // string to our text view.
                    //responseTV.setText(responseString)
                    /*if (responseFromAPI !== null) {
                    Glide.with(this@DashBoardActivity)
                        .load(responseFromAPI?.data[0].iconUrl)
                        .into(imageview)
                } else {
                    imageview.setImageResource(R.drawable.ic_launcher_background)
                }*/
                }
                else
                {
                    Toast.makeText(this@LoginActivity,""+responseFromAPI?.message,Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>?,
                t: Throwable
            ) { // setting text to our text view when
                // we get error response from API.
                // responseTV.setText("Error found is : " + t.message)
                Toast.makeText(this@LoginActivity,""+t.message,Toast.LENGTH_LONG).show()
            }
        })

    }
}