package com.example.yournal

import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale


class Trip(newId: Int, newStartValue: Int = 0, newEndValue: Int = 0,
           newDay : Date = Date.from(Instant.now()),
            newIsCompanyTrip : Boolean = false,
            newFrom : String = "",
            newTo : String = "",
            newDesc:String = "")
{
    private val id = newId
    private var startValue : Int = newStartValue
    private var endValue : Int = newEndValue
    var day : Date = newDay
    private var isCompanyTrip : Boolean = newIsCompanyTrip
    private var from : String = newFrom
    private var where : String = newTo
    private var description : String = newDesc

    fun getEndValue():Int{
        return endValue
    }
    fun getString(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("sv", "SE"))
        return id.toString() + " "+ formatter.format(day)+ " " + startValue.toString()+ " "+
                from +"->"+ where + " " + endValue.toString()
    }

    fun getSaveString(): String{
        val trimFrom = trimHak(from)
        val trimWhere = trimHak(where)
        val trimDesc = trimHak(description)
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
            if(!(a == '[' || a== ']'))
            {
                hak += a
            }
        }

        return hak
    }
}

object TripManager{
    private var currentTripValue = 0
    private var trips  = arrayListOf<Trip>()
    private var nextId : Int = 0

    fun getCurrentTripValue():Int{
        return currentTripValue
    }

    fun addTrip(newTrip: Trip, context: Context){
        currentTripValue = newTrip.getEndValue()
        trips.add(newTrip)
        nextId++
        sortTripsByDate()
        saveToFile(context)
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

        sortTripsByDate()

        saveStringToFile(getSettingString(),"settings.txt", context)
        saveStringToFile(getSaveStringList(), "saved.txt", context)
    }
fun loadFromFile(context: Context){
    trips.clear()
    File(context.getExternalFilesDir(null), "settings.txt").readLines().forEach { line ->
        var mode = Mode.NOTHING
        var tag = ""
        var value = ""
        for (letter in line) {
            if (mode == Mode.NOTHING) {
                tag = ""
                value = ""
            }


            if (letter == '[') {
                mode = Mode.STARTTAG
            } else if (letter == '/') {
                mode = Mode.ENDTAG
            } else if (letter == ']') {
                if (mode == Mode.ENDTAG) {
                    mode = Mode.DONE
                } else if (mode == Mode.TAG) {
                    mode = Mode.VALUE
                }
            } else {
                when (mode) {
                    Mode.STARTTAG -> {
                        tag = ""
                        mode = Mode.TAG
                        tag += letter
                    }

                    Mode.TAG -> {
                        tag += letter
                    }

                    Mode.VALUE -> {
                        value += letter
                    }

                    else -> {}
                }
            }
            if (mode == Mode.DONE) {
                //Här ska vi göra olika saker beroende på activetag
                when (tag) {
                    "LatestTripValue" -> currentTripValue = value.toInt()
                }

                mode = Mode.NOTHING
                value = ""
                tag = ""
            }

        }

    }
    var highestId = 0
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
                when (mode) {
                    Mode.STARTTAG -> {
                        tag = ""
                        mode = Mode.TAG
                        tag += letter
                    }
                    Mode.TAG -> {
                        tag += letter
                    }
                    Mode.VALUE -> {
                        value+=letter
                    }
                    else->{}
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
        val format = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        var rightDate : Date = Date.from(Instant.now())
        try {
            rightDate = format.parse(date) ?: throw IllegalArgumentException("Invalid date format")
        } catch (e: Exception) {
            Log.d("Date error","Error parsing date: ${e.message}")
        }

        if(highestId < id.toInt())
        {
            highestId = id.toInt()
        }
        trips.add(Trip(id.toInt(), startValue.toInt(), endValue.toInt(),rightDate,
            company.toBoolean(),from,to,desc))
    }

    nextId = highestId +1
}
    private fun sortTripsByDate()
    {
        trips.sortByDescending { it.day }
    }

    fun deleteAll(context: Context){
        trips.clear()
        currentTripValue = 0
        nextId = 0
        saveStringToFile(listOf(), "settings.txt", context)
        saveStringToFile(listOf(), "saved.txt", context)
    }

    private fun saveStringToFile(text : List<String>, fileName: String, context: Context){
        val externalFile = File(context.getExternalFilesDir(null), fileName)
        externalFile.writeText("")
        for(row in text)
        {
            externalFile.appendText(row + "\n")
        }

    }

    private fun getSettingString():List<String>{
        val text = mutableListOf<String>()
        text += "[LatestTripValue]"+ getCurrentTripValue() +"[/LatestTripValue]"
        return text
    }
}


enum class Mode{
    STARTTAG, TAG, VALUE, NOTHING, ENDTAG, DONE
}