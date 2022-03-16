package app.com.goparyatak

import DashBoardData
import ReclerViewClickListner
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RvAdapter(val reclerViewClickListner:ReclerViewClickListner) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    lateinit var context: Context
    var userList:MutableList<DashBoardData.Data> = mutableListOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        context = p0.getContext()
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.adapter_item_layout, p0, false)
        return ViewHolder(v);
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.name?.text = userList[p1].iconTaxt
      //  p0.count?.text = userList[p1].count.toString()
        if (userList[p1].iconUrl != null && context != null) {
                    Glide.with(context)
                        .load(userList[p1].iconUrl)
                        .into(p0.image)
                } else {
            p0.image.setImageResource(R.drawable.ic_launcher_background)
                }
        p0.itemView.setOnClickListener {
            reclerViewClickListner.onCellClickListener(userList[p1])
        }
    }
    fun feedData(data: MutableList<DashBoardData.Data>)
    {
        userList = data
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvName)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
       // val count = itemView.findViewById<TextView>(R.id.tvCount)


    }
}