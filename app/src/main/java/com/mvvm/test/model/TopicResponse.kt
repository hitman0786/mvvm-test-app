package com.mvvm.test.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopicResponse(val documents: List<TopicData>): Parcelable

@Parcelize
data class TopicData (val fields: Fields): Parcelable
@Parcelize
data class Fields(val topic: Topic, val description: Description): Parcelable
@Parcelize
data class Topic(val stringValue: String): Parcelable
@Parcelize
data class Description(val stringValue: String): Parcelable