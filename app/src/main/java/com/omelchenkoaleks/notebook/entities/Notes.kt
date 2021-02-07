package com.omelchenkoaleks.notebook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
class Notes : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "subtitle")
    var subtitle: String? = null

    @ColumnInfo(name = "datetime")
    var datetime: String? = null

    @ColumnInfo(name = "textnote")
    var textnote: String? = null

    @ColumnInfo(name = "imagepath")
    var imagepath: String? = null

    @ColumnInfo(name = "weblink")
    var weblink: String? = null

    @ColumnInfo(name = "color")
    var color: String? = null


    override fun toString(): String {
        return "$title : $datetime"
    }

}