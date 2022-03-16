package app.com.goparyatak

import DashBoardData
import DashBoardModel
import DocumentList
import ReclerViewClickListner
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.goparyatak.Retrofit.APIService
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DashBoardActivity : AppCompatActivity(),ReclerViewClickListner {
lateinit var toolbar: Toolbar
lateinit var drawerLayout:DrawerLayout
lateinit var navigationView:NavigationView
lateinit var userName:TextView
lateinit var rvAdapter: RvAdapter
lateinit var recylervView:RecyclerView
var userId:Int = 0
    var session: SessionManager? = null
    lateinit var userNameIntent:String
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        userName = findViewById(R.id.user_name_tv)
        //        initialize the recyclerView from the XML
recylervView = findViewById(R.id.recylerView)
        rvAdapter = RvAdapter(this)
        userNameIntent = intent.getStringExtra("customer_name").toString()

        session = SessionManager(applicationContext)

        //val text = "<font color=#cc0029>Welcome</font> <font color=#000000>Sachin Tupekar</font>"
        userName.text = userNameIntent

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)


        //        initialize the recyclerView from the XML
        //        Initializing the type of layout, here I have used LinearLayoutManager you can try GridLayoutManager
        //        Based on your requirement to allow vertical or horizontal scroll , you can change it in  LinearLayout.VERTICAL
        recylervView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        /*//        Create an arraylist
        val dataList = ArrayList<Model>()
        dataList.add(Model("Phone", 1))
        dataList.add(Model("Watch", 2))
        dataList.add(Model("Note", 3))
        dataList.add(Model("Pin", 4))
        //        pass the values to RvAdapter
        val rvAdapter = RvAdapter(dataList)*/
        //        set the recyclerView to the adapter
        recylervView.adapter = rvAdapter

       var drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
               // setTitle(R.string.app_name)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
              //  setTitle(R.string.help_text)
            }
        }

        drawerToggle.isDrawerIndicatorEnabled = false
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        userId = intent.getIntExtra("customer_id",0)
        rawJSON()

    }
    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        val down = menu?.findItem(R.id.action_logout)
        down?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                session?.logoutUser()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        // passing data from our text fields to our modal class.

        if(userId !=0) { // passing data from our text fields to our modal class.
            val modal = DashBoardModel(userId, "500", "7cffaaaf6b7d5fcfc6dbfb020c017d1a")

            // calling a method to create a post and passing our modal class.

            // calling a method to create a post and passing our modal class.
            val call: Call<DashBoardData?>? = retrofitAPI.dashBoardData(modal)

            // on below line we are executing our method.

            // on below line we are executing our method.
            call?.enqueue(object : Callback<DashBoardData?> {
                override fun onResponse(
                    call: Call<DashBoardData?>?, response: Response<DashBoardData?>
                ) { // this method is called when we get response from our api.



                    val responseFromAPI: DashBoardData? = response.body()
                    Log.d("DashboardResponse", "" + responseFromAPI)
                    rvAdapter.notifyDataSetChanged()
                    responseFromAPI?.data?.let { rvAdapter.feedData(it) }

                }

                override fun onFailure(
                    call: Call<DashBoardData?>?, t: Throwable
                ) { // setting text to our text view when
                    // we get error response from API.
                    // responseTV.setText("Error found is : " + t.message)
                }
            })
        }
        else
        {
            Toast.makeText(this,"Invalid customer Id",Toast.LENGTH_LONG)
        }

    }

    override fun onCellClickListener(data: DashBoardData.Data) {
        val intent = Intent(applicationContext,BookingDetailsActivity::class.java)
        intent.putExtra("customer_id",userId)
        intent.putExtra("category_id",data.categoryId)
        startActivity(intent)
    }

    override fun onCellDetailsClickListener(data: DashBoardData1.Data) {
        TODO("Not yet implemented")
    }

    override fun documentClickListener(data: DocumentList) {
        TODO("Not yet implemented")
    }

}