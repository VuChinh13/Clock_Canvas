package com.example.firstapp.testservice2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(var name: String, val age: Int) : Parcelable