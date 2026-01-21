package com.example.firstapp.testforegroundservice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(var name: String, val age: Int) : Parcelable