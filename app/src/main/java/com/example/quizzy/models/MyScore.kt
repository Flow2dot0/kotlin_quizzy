package com.example.quizzy.models

import android.os.Parcel
import android.os.Parcelable

data class MyScore(var date: String? = null, var first: Boolean? = null, var second: Boolean? = null, var third: Boolean? = null, var fourth: Boolean? = null, var fifth: Boolean? = null, var correct: Int? = null, var winrate: Int? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeValue(first)
        parcel.writeValue(second)
        parcel.writeValue(third)
        parcel.writeValue(fourth)
        parcel.writeValue(fifth)
        parcel.writeValue(correct)
        parcel.writeValue(winrate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyScore> {
        override fun createFromParcel(parcel: Parcel): MyScore {
            return MyScore(parcel)
        }

        override fun newArray(size: Int): Array<MyScore?> {
            return arrayOfNulls(size)
        }
    }
}