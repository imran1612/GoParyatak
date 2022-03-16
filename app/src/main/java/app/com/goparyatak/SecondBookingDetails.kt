package app.com.goparyatak

import DashBoardData
import DashBoardData1.Data
import DocumentList
import ReclerViewClickListner
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.DialogInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.goparyatak.Retrofit.DocumentAdapter
import java.io.File
import java.net.URL


class SecondBookingDetails : AppCompatActivity(),ReclerViewClickListner {
lateinit var booking_label:TextView
lateinit var booking_date:TextView
lateinit var booking_id:TextView
lateinit var booking_no:TextView
lateinit var booking_note:TextView
lateinit var identity_no:TextView
lateinit var travels_note:TextView
    lateinit var recylervView: RecyclerView
    lateinit var documentAdapter: DocumentAdapter
    var manager: DownloadManager? = null
lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_booking)
        booking_label = findViewById(R.id.booking_label)
        booking_date = findViewById(R.id.booking_date)
        booking_no = findViewById(R.id.booking_no)
        booking_note = findViewById(R.id.booking_note)
        identity_no = findViewById(R.id.identity_no)
        travels_note = findViewById(R.id.travels_note)
        recylervView = findViewById(R.id.recylerView)
        documentAdapter = DocumentAdapter(this)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        booking_label.text = "Booking Label:"+intent.getStringExtra("booking_label")
        booking_date.text = "Booking Date:"+intent.getStringExtra("booking_date")
        booking_no.text = "Booking No:"+intent.getIntExtra("booking_no",0).toString()
        identity_no.text = "Identity No:"+intent.getStringExtra("identity_no")
        travels_note.text = "Travels Note:"+intent.getStringExtra("travels_note")
        booking_note.text = "Booking Note:"+intent.getStringExtra("booking_note")
        val bundle = intent.extras
        val arraylist: ArrayList<DocumentList> = bundle?.getParcelableArrayList("mydoclist")!!

        var doc:MutableList<Data.Document> = mutableListOf()
        if (arraylist != null) {
            for (d in arraylist) {
                var x: Data.Document = Data.Document()
                x.documentId = d.documentId
                x.documentLable = d.documentLable
                x.documentUrl = d.documentUrl
               doc.add(x)
            }
        }
        var x = arraylist?.count()



        recylervView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recylervView.adapter = documentAdapter

documentAdapter.feedData(arraylist)
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

    override fun onCellClickListener(data: DashBoardData.Data) {
        TODO("Not yet implemented")
    }

    override fun onCellDetailsClickListener(data: Data) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun documentClickListener(data: DocumentList) {
        /*manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(data.documentUrl)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        val reference = manager?.enqueue(request)*/

        showDialog(data)




    }
    private fun download(data: DocumentList) {
        var url = URL(data.documentUrl)
        var fileName = url.path
        fileName = fileName.substring(fileName.lastIndexOf("/")+1)

        if(!isFileExists(fileName)) {
            var request: DownloadManager.Request =
                DownloadManager.Request(Uri.parse(data.documentUrl + ""))
            request.setTitle(fileName)
            request.setMimeType("application/pdf")
            request.allowScanningByMediaScanner()
            request.setAllowedOverMetered(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            var dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }
        else {
            overrideShowDialog(data)
        }
    }
    private fun downloadAnyway(data: DocumentList) {
        var url = URL(data.documentUrl)
        var fileName = url.path
        fileName = fileName.substring(fileName.lastIndexOf("/")+1)

        deleteFile1(fileName,data)
        /*var request: DownloadManager.Request =
                DownloadManager.Request(Uri.parse(data.documentUrl + ""))
            request.setTitle(fileName)
            request.setMimeType("application/pdf")
            request.allowScanningByMediaScanner()
            request.setAllowedOverMetered(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            var dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)*/

    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(data: DocumentList){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Download")

        // Set a message for alert dialog
        builder.setMessage("Do you want to download file ?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> download(data)
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)



        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun overrideShowDialog(data: DocumentList){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Download")

        // Set a message for alert dialog
        builder.setMessage("The file already download . Do you want to overwrite it?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> downloadAnyway(data)
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)



        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun isFileExists(filename: String): Boolean {
        val folder1 = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath +"/"+ filename)

        return folder1.isFile()
    }

    private fun deleteFile1(filename: String, data: DocumentList): Boolean {
        val folder1 = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath +"/"+ filename)
        return folder1.delete()
    }

}




