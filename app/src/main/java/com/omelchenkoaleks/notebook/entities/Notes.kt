package com.omelchenkoaleks.notebook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "subtitle")
    var subtitle: String,

    @ColumnInfo(name = "datetime")
    var datetime: String,

    @ColumnInfo(name = "textnote")
    var textnote: String,

    @ColumnInfo(name = "imagepath")
    var imagepath: String,

    @ColumnInfo(name = "weblink")
    var weblink: String,

    @ColumnInfo(name = "color")
    var color: String

) : Serializable {

    override fun toString(): String {
        return "$title : $datetime"
    }

}