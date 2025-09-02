package com.ndy

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Pet Store", description = "Pet Store inventory operations")
class StoreResource @Inject constructor(
    private val petStoreService: PetStoreService
) {
    
    @GET
    @Path("/inventory")
    @Operation(summary = "Get store inventory", description = "Returns pet inventories by status")
    @APIResponse(
        responseCode = "200", 
        description = "Successful operation",
        content = [Content(schema = Schema(implementation = Map::class))]
    )
    fun getInventory(): Uni<Map<String, Int>> {
        return petStoreService.getInventoryUni()
    }
    
    @GET
    @Path("/inventory/coroutine")
    @Operation(summary = "Get store inventory with coroutine", description = "Returns pet inventories by status using suspend function")
    @APIResponse(
        responseCode = "200", 
        description = "Successful operation",
        content = [Content(schema = Schema(implementation = Map::class))]
    )
    suspend fun getInventoryCoroutine(): Map<String, Int> {
        return petStoreService.getInventory()
    }
}
