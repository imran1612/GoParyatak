


import com.google.gson.annotations.SerializedName

data class LoginFailureResponse(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)