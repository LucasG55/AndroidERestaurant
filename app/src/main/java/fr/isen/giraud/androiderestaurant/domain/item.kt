package fr.isen.giraud.androiderestaurant.domain

data class item (
    val id : String,
    val name_fr : String,
    val name_en : String,
    val id_category : String,
    val categ_name_fr : String,
    val categ_name_en : String,
    val images : Array<String>,
    val ingredients : Array<ingredient>,
    val prices : Array<price>
    )