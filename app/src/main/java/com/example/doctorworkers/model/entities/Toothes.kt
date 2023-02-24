package com.example.doctorworkers.model.entities

import androidx.annotation.DrawableRes
import com.example.doctorworkers.R

sealed class Toothes(
    @DrawableRes val icon: Int,
    val description: String,
    val id: String
) {
    object BrokenTooth : Toothes(R.drawable.ic_broken_tooth, "Поврежденный", "broken")
    object HealthyTooth : Toothes(R.drawable.ic_tooth, "Здоровый", "healthy")
    object InWorkTooth : Toothes(R.drawable.ic_tooth_heart, "Лечится", "inWork")
    object СariesTooth : Toothes(R.drawable.ic_caries_tooth, "Кариес", "caries")
}

fun Toothes.getNextToothType(): Toothes {
    return when(this) {
        is Toothes.BrokenTooth -> Toothes.InWorkTooth
        is Toothes.InWorkTooth -> Toothes.HealthyTooth
        is Toothes.HealthyTooth -> Toothes.СariesTooth
        else -> Toothes.BrokenTooth
    }
}
