
import com.google.gson.annotations.SerializedName

data class DashBoardData(
    @SerializedName("data")
    val `data`: MutableList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("icon_taxt")
        val iconTaxt: String,
        @SerializedName("icon_url")
        val iconUrl: String
    )
}