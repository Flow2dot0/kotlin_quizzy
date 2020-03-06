package com.example.quizzy.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class MyScore(
	var date: Timestamp? = null,
	var first: Boolean? = null,
	var second: Boolean? = null,
	var third: Boolean? = null,
	var fourth: Boolean? = null,
	var fifth: Boolean? = null,
	var correct: Int? = null,
	var winrate: Int? = null,
	var level: Int? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Timestamp::class.java.classLoader) as? Timestamp,
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Int::class.java.classLoader) as? Int
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(date)
		parcel.writeValue(first)
		parcel.writeValue(second)
		parcel.writeValue(third)
		parcel.writeValue(fourth)
		parcel.writeValue(fifth)
		parcel.writeValue(correct)
		parcel.writeValue(winrate)
		parcel.writeValue(level)
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