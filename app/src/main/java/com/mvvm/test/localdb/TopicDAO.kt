package com.mvvm.test.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mvvm.test.model.TopicResponse

@Dao
interface TopicDAO {
    @Query("SELECT * FROM topicresponse")
    fun getAll(): List<TopicResponse>

    @Insert
    fun insertAll(vararg todo: TopicResponse)

    @Query("DELETE FROM topicresponse")
    fun delete()
}