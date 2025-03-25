package ca.uqac.tp_mobile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine")
data class Routine (
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    val title : String,
    val description : String,
    val day : List<Int>,
    val hour : String,
    val location : String,
    val priority : Int
)