package com.example.quizzy.models

import android.os.Parcel
import android.os.Parcelable

data class MyQuestion(var id : Int? = null, var level : Int? = null, var path : String? = null, var title : String? = null, var answer : String? = null, var A : String? = null, var B : String? = null, var C : String? = null, var D : String? = null, var pathMode : String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(level)
        parcel.writeString(path)
        parcel.writeString(title)
        parcel.writeString(answer)
        parcel.writeString(A)
        parcel.writeString(B)
        parcel.writeString(C)
        parcel.writeString(D)
        parcel.writeString(pathMode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyQuestion> {
        override fun createFromParcel(parcel: Parcel): MyQuestion {
            return MyQuestion(parcel)
        }

        override fun newArray(size: Int): Array<MyQuestion?> {
            return arrayOfNulls(size)
        }
    }


}