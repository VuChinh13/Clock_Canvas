package com.example.firstapp.testservice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FileItem(val name: String, val path: String) : Parcelable