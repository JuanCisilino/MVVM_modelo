package com.frost.model_mvvm.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.getPref() = getSharedPreferences("prefs_file", Context.MODE_PRIVATE)

fun Activity.savePrefs(name:String, value: Double){
    val sharedPreferences = getPref()
    val editor = sharedPreferences.edit()
    editor.putString(name, value.toString())
    editor.apply()
}

fun Activity.isSharedEmpty(): Boolean {
    val sharedPreferences = getPref()
    val blue = sharedPreferences.getString("blue", "")
    return blue.isNullOrEmpty()
}

fun Activity.saveDate(){
    val sharedPreferences = getPref()
    val timestampActual = getActualDate()
    val editor = sharedPreferences.edit()
    editor.putString("date", timestampActual.toString())
    editor.apply()
}

private fun Activity.getActualDate() = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault())

fun Activity.isOtherDay(): Boolean{
    val sharedPreferences = getPref()
    val timestampGuardado = sharedPreferences.getString("date", null)
    timestampGuardado?:run{ return true }
    val localDateGuardado = LocalDateTime.parse(timestampGuardado)
    val localDateActual = getActualDate()
    val diff = ChronoUnit.DAYS.between(localDateGuardado, localDateActual)
    return diff > 1
}

fun Activity.clearPrefs(){
    val sharedPreferences = getPref()
    sharedPreferences?.edit()?.clear()?.apply()
}

fun Activity.hide(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}