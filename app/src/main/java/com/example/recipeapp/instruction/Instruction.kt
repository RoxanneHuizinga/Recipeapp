package com.example.recipeapp.instruction

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Instruction (
    var step: String,
    var instruction: String
): Parcelable