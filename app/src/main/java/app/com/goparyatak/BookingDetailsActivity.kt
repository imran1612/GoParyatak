package app.com.goparyatak

import DashBoardData1
import DocumentList
import ReclerViewClickListner
import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.goparyatak.Retrofit.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BookingDetailsActivity: AppCompatActivity(),ReclerViewClickListner {
    var userId:Int = 0
    var categoryId = 0
    lateinit var bookingDetailsAdapter:BookingDetailsAdapter
    lateinit var recylervView:RecyclerView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookingdetails)

        userId = intent.getIntExtra("customer_id",0)
        categoryId = intent.getIntExtra("category_id",0)
        bookingDetailsAdapter = BookingDetailsAdapter(this)
        recylervView = findViewById(R.id.recylerView)
        recylervView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recylervView.adapter = bookingDetailsAdapter
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        rawJSON()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun rawJSON() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://swarajitservices.co.in/goparyatak/Apiv1/") // as we are sending data in json format so
            // we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create()) // at last we are building our retrofit builder.
            .build() // below line is to create an instance for our retrofit api class.
        // below line is to create an instance for our retrofit api class.
        val retrofitAPI: APIService = retrofit.create(APIService::class.java)

        // passing data from our text fields to our modal class.

        if(userId !=0 && categoryId != 0) { // passing data from our text fields to our modal class.
            val modal = UserInfo(userId, "500", categoryId,"7cffaaaf6b7d5fcfc6dbfb020c017d1a")

            // calling a method to create a post and passing our modal class.

            // calling a method to create a post and passing our modal class.
            val call: Call<DashBoardData1?>? = retrofitAPI.bookingDetail(modal)

            // on below line we are executing our method.

            // on below line we are executing our method.
            call?.enqueue(object : Callback<DashBoardData1?> {
                override fun onResponse(
                    call: Call<DashBoardData1?>?, response: Response<DashBoardData1?>
                ) { // this method is called when we get response from our api.



                    val responseFromAPI: DashBoardData1? = response.body()
                    Log.d("DashboardResponse", "" + responseFromAPI)
                    bookingDetailsAdapter.notifyDataSetChanged()
                    responseFromAPI?.data?.let { bookingDetailsAdapter.feedData(it) }

                }

                override fun onFailure(
                    call: Call<DashBoardData1?>?, t: Throwable
                ) { // setting text to our text view when
                    // we get error response from API.
                    // responseTV.setText("Error found is : " + t.message)
                }
            })
        }
        else
        {
            Toast.makeText(this,"Invalid customer Id", Toast.LENGTH_LONG)
        }

    }

    override fun onCellClickListener(data: DashBoardData.Data) {
        TODO("Not yet implemented")
    }

    override fun onCellDetailsClickListener(data: DashBoardData1.Data) {
        val intent = Intent(applicationContext,SecondBookingDetails::class.java)
        var list:ArrayList<DocumentList> = ArrayList()
        for(doc in data.documentList)
        {
            var x:DocumentList = DocumentList()
            x.documentId = doc.documentId
            x.documentLable = doc.documentLable
            x.documentUrl = doc.documentUrl
            list.add(x)
        }
        val bundle = Bundle()
        bundle.putParcelableArrayList("mydoclist", list)
        intent.putExtras(bundle)
        intent.putExtra("booking_label",data.bookingLabel)
        intent.putExtra("booking_date",data.bookingDate)
        intent.putExtra("booking_id",data.bookingId)
        intent.putExtra("booking_no",data.bookingNo)
        intent.putExtra("booking_note",data.bookingNote)
        intent.putExtra("identity_no",data.identityNo)
        intent.putExtra("travels_note",data.travelsNote)
        startActivity(intent)
    }

    override fun documentClickListener(data: DocumentList) {
        TODO("Not yet implemented")
    }
}