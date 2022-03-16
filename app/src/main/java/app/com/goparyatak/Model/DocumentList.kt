

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DocumentList(
    var documentId: Int? = 0,
    var documentLable: String? = null,
    var documentUrl: String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(documentId)
        parcel.writeString(documentLable)
        parcel.writeString(documentUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DocumentList> {
        override fun createFromParcel(parcel: Parcel): DocumentList {
            return DocumentList(parcel)
        }

        override fun newArray(size: Int): Array<DocumentList?> {
            return arrayOfNulls(size)
        }
    }
}
