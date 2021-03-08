package zzz.bing.ticketunion.model.domain

import android.os.Parcel
import android.os.Parcelable

data class TicketParcelable(
    val url: String,
    val title: String,
    val pic: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(url)
        dest?.writeString(title)
        dest?.writeString(pic)
    }

    companion object CREATOR : Parcelable.Creator<TicketParcelable> {
        override fun createFromParcel(parcel: Parcel): TicketParcelable {
            return TicketParcelable(parcel)
        }

        override fun newArray(size: Int): Array<TicketParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
