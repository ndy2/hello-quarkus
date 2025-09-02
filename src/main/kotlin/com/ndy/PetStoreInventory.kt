package com.ndy

import kotlinx.serialization.Serializable

@Serializable
data class PetStoreInventory(
    val available: Int? = null,
    val pending: Int? = null,
    val sold: Int? = null
)
