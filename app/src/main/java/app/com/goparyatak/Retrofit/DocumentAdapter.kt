package app.com.goparyatak.Retrofit

import DocumentList
import ReclerViewClickListner
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.com.goparyatak.R
import java.net.URL
import java.util.ArrayList

class DocumentAdapter(val reclerViewClickListner:ReclerViewClickListner) : RecyclerView.Adapter<DocumentAdapter.ViewHolder>() {
    lateinit var context: Context
    var userList:ArrayList<DocumentList> = ArrayList()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        context = p0.getContext()
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.simple_list_layout, p0, false)
        return ViewHolder(v);
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.label?.text = userList[p1].documentLable
        var url = URL(userList[p1].documentUrl)
        var fileName = url.path
        fileName = fileName.substring(fileName.lastIndexOf("/")+1)
        p0.link?.text = "\u25CF"+ fileName
        p0.itemView.setOnClickListener {
            reclerViewClickListner.documentClickListener(userList[p1])
        }
    }
    fun feedData(data: ArrayList<DocumentList>)
    {
        userList = data
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label = itemView.findViewById<TextView>(R.id.idLabel)
        val link = itemView.findViewById<TextView>(R.id.downloadLink)
        // val count = itemView.findViewById<TextView>(R.id.tvCount)


    }
}