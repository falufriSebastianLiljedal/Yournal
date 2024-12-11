package com.example.yournal

import java.time.Instant
import java.util.Date


class Trip(id: Int, newStartValue: Int = 0, newEndValue: Int = 0,
           newDay : Date = Date.from(Instant.now()),
            newIsCompanyTrip : Boolean = false,
            newFrom : String = "",
            newTo : String = "",
            newDesc:String = "")
{
    val id = id

    var startValue : Int = newStartValue
    var endValue : Int = newEndValue
    var day : Date = newDay
    var isCompanyTrip : Boolean = newIsCompanyTrip
    var from : String = newFrom
    var where : String = newTo
    var description : String = newDesc

    fun getString(): String{
        return day.toString() + " " + from +"->"+ where
    }
}

object TripManager{
    private var trips  = arrayListOf<Trip>()
    private var nextId : Int = 0

    fun AddTrip(newTrip: Trip){
        trips.add(newTrip)
        nextId++
    }
    fun getNextId():Int{
        return nextId
    }

    fun getStringTripList(): List<String>{
        val list = arrayListOf<String>()
        for(ob in trips)
        {
            list.add(ob.getString())
        }
        return list
    }
}