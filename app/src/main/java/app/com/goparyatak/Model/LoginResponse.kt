


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("address")
        val address: String? = null,
        @SerializedName("age")
        val age: Int? = 0,
        @SerializedName("gender")
        val gender: String? = null,
        @SerializedName("mobile")
        val mobile: Long? = 0,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("customerid")
        val userid: Int? = 0
    )
}