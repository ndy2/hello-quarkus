package com.ndy

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@RegisterRestClient(configKey = "petstore-api")
@Path("/v2")
interface PetStoreClient {
    
    @GET
    @Path("/store/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    fun getInventory(): Uni<Map<String, Int>>
    
    @GET
    @Path("/store/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getInventoryCoroutine(): Map<String, Int>
}
