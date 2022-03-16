package app.com.goparyatak

import ReclerViewClickListner
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookingDetailsAdapter(val reclerViewClickListner:ReclerViewClickListner) : RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder>() {
    lateinit var context: Context
    var userList:MutableList<DashBoardData1.Data> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        context = p0.getContext()
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.booking_details_card, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.booking_label?.text = userList[p1].bookingLabel
        p0.booking_date.text = "Booking Date\n"+userList[p1].bookingDate

        p0.itemView.setOnClickListener {
            reclerViewClickListner.onCellDetailsClickListener(userList[p1])
        }
    }
    fun feedData(data: MutableList<DashBoardData1.Data>)
    {
        userList = data
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val booking_label = itemView.findViewById<TextView>(R.id.booking_label)
        val booking_date = itemView.findViewById<TextView>(R.id.booking_date)
        // val count = itemView.findViewById<TextView>(R.id.tvCount)


    }
}