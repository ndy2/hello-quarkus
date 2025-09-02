package com.ndy

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Path("/sms-result")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "SMS Result", description = "SMS Result CRUD operations")
class SmsResultResource {
    private val repository: SmsResultRepository = InMemorySmsResultRepository()

    @POST
    @Operation(summary = "Create SMS result", description = "Create a new SMS result")
    @APIResponse(
        responseCode = "201", 
        description = "SMS result created successfully",
        content = [Content(schema = Schema(implementation = SmsResult::class))]
    )
    fun create(result: SmsResult): Uni<Response> {
        return repository.create(result)
            .map { created -> Response.status(Response.Status.CREATED).entity(created).build() }
    }

    @GET
    @Path("/{hash}")
    @Operation(summary = "Get SMS result", description = "Get SMS result by hash")
    @APIResponse(
        responseCode = "200", 
        description = "SMS result found",
        content = [Content(schema = Schema(implementation = SmsResult::class))]
    )
    @APIResponse(responseCode = "404", description = "SMS result not found")
    fun read(
        @Parameter(description = "SMS result hash") @PathParam("hash") hash: String
    ): Uni<Response> {
        return repository.read(hash)
            .map { result ->
                if (result != null) {
                    Response.ok(result).build()
                } else {
                    Response.status(Response.Status.NOT_FOUND).build()
                }
            }
    }

    @PUT
    @Path("/{hash}")
    @Operation(summary = "Update SMS result", description = "Update SMS result by hash")
    @APIResponse(
        responseCode = "200", 
        description = "SMS result updated successfully",
        content = [Content(schema = Schema(implementation = SmsResult::class))]
    )
    @APIResponse(responseCode = "404", description = "SMS result not found")
    fun update(
        @Parameter(description = "SMS result hash") @PathParam("hash") hash: String, 
        updated: SmsResult
    ): Uni<Response> {
        return repository.update(hash, updated)
            .map { updatedResult ->
                if (updatedResult != null) {
                    Response.ok(updatedResult).build()
                } else {
                    Response.status(Response.Status.NOT_FOUND).build()
                }
            }
    }

    @DELETE
    @Path("/{hash}")
    @Operation(summary = "Delete SMS result", description = "Delete SMS result by hash")
    @APIResponse(responseCode = "204", description = "SMS result deleted successfully")
    @APIResponse(responseCode = "404", description = "SMS result not found")
    fun delete(
        @Parameter(description = "SMS result hash") @PathParam("hash") hash: String
    ): Uni<Response> {
        return repository.delete(hash)
            .map { deleted ->
                if (deleted) {
                    Response.noContent().build()
                } else {
                    Response.status(Response.Status.NOT_FOUND).build()
                }
            }
    }

    @GET
    @Operation(summary = "List all SMS results", description = "Get all SMS results")
    @APIResponse(
        responseCode = "200", 
        description = "List of SMS results",
        content = [Content(schema = Schema(implementation = Array<SmsResult>::class))]
    )
    fun list(): Uni<List<SmsResult>> = repository.list()
}