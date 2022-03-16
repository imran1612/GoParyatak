


import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DashBoardData1(
    @SerializedName("data")
    val `data`: MutableList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("booking_date")
        val bookingDate: String,
        @SerializedName("booking_id")
        val bookingId: Int,
        @SerializedName("booking_label")
        val bookingLabel: String,
        @SerializedName("booking_no")
        val bookingNo: Int,
        @SerializedName("booking_note")
        val bookingNote: String,
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("document_list")
        val documentList: List<Document>,
        @SerializedName("identity_no")
        val identityNo: String,
        @SerializedName("travels_note")
        val travelsNote: String
    ) {
        data class Document(
            @SerializedName("document_id") var documentId: Int? = 0,
            @SerializedName("document_lable") var documentLable: String? = null,
            @SerializedName("document_url") var documentUrl: String? = null
        )
    }
}