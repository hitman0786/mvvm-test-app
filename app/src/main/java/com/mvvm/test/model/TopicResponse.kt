package com.mvvm.test.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mvvm.test.localdb.DataConverter
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TopicResponse(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @TypeConverters(DataConverter::class)
    val documents: List<TopicData>?
) : Parcelable {
    constructor() : this(0, null)
}

@Parcelize
data class TopicData(val fields: Fields?) : Parcelable {
    constructor() : this(null)
}

@Parcelize
data class Fields(val topic: Topic?, val description: Description?, val time: Time?) : Parcelable {
    constructor() : this(null, null, null)
}

@Parcelize
data class Topic(val stringValue: String) : Parcelable {
    constructor() : this("")
}

@Parcelize
data class Description(val stringValue: String) : Parcelable {
    constructor() : this("")
}

@Parcelize
data class Time(val stringValue: String) : Parcelable {
    constructor() : this("")
}