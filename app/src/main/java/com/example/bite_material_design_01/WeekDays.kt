package com.example.bite_material_design_01

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints
import java.util.*

class WeekDays() :CalendarConstraints.DateValidator {

    private val utc: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    override fun isValid(date: Long): Boolean {

        utc.timeInMillis = date
        val dayOfWeek = utc[Calendar.DAY_OF_WEEK]
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.FRIDAY
    }

    companion object CREATOR : Parcelable.Creator<WeekDays> {
        override fun createFromParcel(parcel: Parcel): WeekDays {
            return WeekDays(parcel)
        }

        override fun newArray(size: Int): Array<WeekDays?> {
            return arrayOfNulls(size)
        }
    }

}