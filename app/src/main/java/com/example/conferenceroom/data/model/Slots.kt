package com.example.conferenceroom.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.conferenceroom.data.DateConverter
import com.example.conferenceroom.util.Constants
import java.util.*

@Entity(tableName = Constants.TABLE_NAME)
data class Slots(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "slot_id")
    var userId: Long = 0,

    @ColumnInfo(name = "created_date")
    @TypeConverters(DateConverter::class)
    var createDate: Date? = null,

    @ColumnInfo(name = "start_time")
    @TypeConverters(DateConverter::class)
    var start_time: Date? = null,

    @ColumnInfo(name = "room")
    var room: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "end_time")
    @TypeConverters(DateConverter::class)
    var end_time: Date? = null

) : Parcelable{
    constructor(source: Parcel) : this(
    source.readLong(),
    source.readSerializable() as Date?,
    source.readSerializable() as Date?,
    source.readString(),
    source.readString(),
    source.readSerializable() as Date?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(userId)
        writeSerializable(createDate)
        writeSerializable(start_time)
        writeString(room)
        writeString(title)
        writeSerializable(end_time)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Slots> = object : Parcelable.Creator<Slots> {
            override fun createFromParcel(source: Parcel): Slots =
                Slots(source)
            override fun newArray(size: Int): Array<Slots?> = arrayOfNulls(size)
        }
    }
}

