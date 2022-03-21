package fr.isen.giraud.androiderestaurant.domain

data class Data (
    val name_fr :String,
    val name_en :String,
    val items : Array<item>,
)