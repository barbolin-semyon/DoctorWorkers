package com.example.doctors.util

import com.example.doctorworkers.model.entities.Toothes


fun parseListIdToListToothes(listId: List<String>): MutableList<Toothes> {
    val toothes = mutableListOf<Toothes>()
    listId.forEach { toothes.add(getToothById(it)) }
    return toothes
}

fun parseListToothesToListId(toothes: List<Toothes>): MutableList<String> {
    val toothesId = mutableListOf<String>()
    toothes.forEach { toothesId.add(it.id) }
    return toothesId
}

fun getNextTypeTooth(tooth: Toothes): Toothes {
    return when(tooth) {
        is Toothes.BrokenTooth -> Toothes.InWorkTooth
        is Toothes.InWorkTooth -> Toothes.HealthyTooth
        is Toothes.HealthyTooth -> Toothes.СariesTooth
        else -> Toothes.BrokenTooth
    }
}

private fun getToothById(toothId: String): Toothes {
    return when(toothId) {
        Toothes.BrokenTooth.id -> Toothes.BrokenTooth
        Toothes.InWorkTooth.id -> Toothes.InWorkTooth
        Toothes.HealthyTooth.id -> Toothes.HealthyTooth
        else -> Toothes.СariesTooth
    }
}
