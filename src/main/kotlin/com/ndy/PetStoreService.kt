package com.ndy

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.rest.client.inject.RestClient

@ApplicationScoped
class PetStoreService @Inject constructor(
    @RestClient private val petStoreClient: PetStoreClient
) {
    
    suspend fun getInventory(): Map<String, Int> {
        return petStoreClient.getInventoryCoroutine()
    }
    
    fun getInventoryUni(): Uni<Map<String, Int>> {
        return petStoreClient.getInventory()
    }
}
