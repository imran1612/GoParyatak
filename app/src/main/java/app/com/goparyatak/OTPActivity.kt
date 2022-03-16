package app.com.goparyatak

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.OnOtpCompletionListener
import com.mukesh.OtpView


class OTPActivity : AppCompatActivity() {
    private var otpView: OtpView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_otp)
        otpView = findViewById(R.id.otp_view)
        /*otpView.callOnClick(OnOtpCompletionListener { otp -> // do Stuff
            Log.d("onOtpCompleted=>", otp)
        })*/
    }
}