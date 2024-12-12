package com.example.yournal

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale


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

    fun getSaveString(): String{
        var trimFrom = trimHak(from)
        var trimWhere = trimHak(where)
        var trimDesc = trimHak(description)
        return "[Post]"+
                    "[ID]"+
                        id.toString()+
                    "[/ID]"+
                    "[StartValue]"+
                        startValue.toString()+
                    "[/StartValue]"+
                    "[EndValue]"+
                        endValue.toString()+
                    "[/EndValue]"+
                    "[Date]"+
                        day.toString()+
                    "[/Date]"+
                    "[Company]"+
                        isCompanyTrip.toString()+
                    "[/Company]"+
                    "[From]"+
                        trimFrom+
                    "[/From]"+
                    "[To]"+
                        trimWhere+
                    "[/To]"+
                    "[Desc]"+
                        trimDesc+
                    "[/Desc]"+
                "[/Post]"
    }

    private fun trimHak(value : String): String{
        var hak = ""

        for(a in value)
        {
            if(a == '[' || a== ']')
            {

            }
            else
            {
                hak += a
            }
        }

        return hak
    }
}

object TripManager{
    private var trips  = arrayListOf<Trip>()
    private var nextId : Int = 0

    fun AddTrip(newTrip: Trip){
        trips.add(newTrip)
        nextId++
        sortTripsByDate()
    }
    fun getNextId():Int{
        return nextId
    }

    fun getStringTripList(): List<String>{
        sortTripsByDate()
        val list = arrayListOf<String>()
        for(ob in trips)
        {
            list.add(ob.getString())
        }
        return list
    }

    private fun getSaveStringList(): List<String>{
        val list = arrayListOf<String>()
        for(ob in trips)
        {
            list.add(ob.getSaveString())
        }

        return list
    }

    fun saveToFile(context: Context){
        Log.d("Save","Saving")
        sortTripsByDate()
        var text = getSaveStringList()
        /*val am = context.assets
        val filename = "settings/test.txt"
        val file = am.open(filename)
        file.writeText()
        */

        val externalFile = File(context.getExternalFilesDir(null), "saved.txt")
        externalFile.writeText("")
        for(row in text)
        {
            externalFile.appendText(row + "\n")
        }


        val externalContent = externalFile.readText()
        Log.d("Reading",externalContent)
    }
fun loadFromFile(context: Context){
    trips.clear()

    File(context.getExternalFilesDir(null), "saved.txt").readLines().forEach()
    {line ->
        var mode = Mode.NOTHING
        var id = ""
        var startValue = ""
        var endValue = ""
        var date = ""
        var company = ""
        var from = ""
        var to = ""
        var desc = ""
        var tag = ""
        var value = ""
        Log.d("Loading", "line")
        for(letter in line)
        {
            if(mode == Mode.NOTHING) {
                tag= ""
                value = ""
            }


            if(letter == '[')
            {
                mode = Mode.STARTTAG
            }
            else if(letter == '/')
            {
                mode = Mode.ENDTAG
            }
            else if(letter == ']'){
                if(mode == Mode.ENDTAG)
                {
                    mode = Mode.DONE
                }
                else if(mode == Mode.TAG)
                {
                    mode = Mode.VALUE
                }
            }
            else{
                if(mode == Mode.STARTTAG)
                {
                    tag = ""
                    mode = Mode.TAG
                    tag += letter
                }
                else if(mode == Mode.TAG)
                {
                    tag += letter
                }
                else if(mode == Mode.VALUE){
                    value+=letter
                }
            }
            if(mode == Mode.DONE)
            {
                //Här ska vi göra olika saker beroende på activetag
                when(tag){
                    "ID"-> id = value
                    "StartValue"-> startValue = value
                    "EndValue"-> endValue = value
                    "Date"-> date = value
                    "Company"-> company = value
                    "From"-> from = value
                    "To"-> to = value
                    "Desc"-> desc = value
                }

                mode = Mode.NOTHING
                value = ""
                tag = ""
            }

        }
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var rightDate : Date = Date.from(Instant.now())
        try {
            rightDate = format.parse(date) ?: throw IllegalArgumentException("Invalid date format")
        } catch (e: Exception) {
            Log.d("Date error","Error parsing date: ${e.message}")
        }
        trips.add(Trip(id.toInt(), startValue.toInt(), endValue.toInt(),rightDate,
            company.toBoolean(),from,to,desc))
    }


}
    private fun sortTripsByDate()
    {
        trips.sortByDescending { it.day }
    }
}

enum class Mode{
    STARTTAG, TAG, VALUE, NOTHING, ENDTAG, DONE
}