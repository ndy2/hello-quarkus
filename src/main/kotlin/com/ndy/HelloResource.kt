package com.ndy

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.delay
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@Tag(name = "Hello", description = "Hello world endpoints")
class HelloResource {
    @GET
    @Path("/coroutine")
    @Operation(summary = "Hello with coroutine", description = "Returns hello message using suspend function")
    @APIResponse(responseCode = "200", description = "Hello message")
    suspend fun helloCoroutine(
        @Parameter(description = "Name to greet") @QueryParam("name") name: String?
    ): String {
        delay(10)
        return if (name.isNullOrBlank()) "hello" else "hello $name"
    }

    @GET
    @Path("/uni")
    @Operation(summary = "Hello with Uni", description = "Returns hello message using Uni")
    @APIResponse(responseCode = "200", description = "Hello message")
    fun helloUni(
        @Parameter(description = "Name to greet") @QueryParam("name") name: String?
    ): Uni<String> {
        val result = if (name.isNullOrBlank()) "hello" else "hello $name"
        return Uni.createFrom().item(result)
    }


    @GET
    @Path("/status/{status}")
    @Operation(summary = "Status response", description = "Returns status value and sets HTTP status code")
    @APIResponse(responseCode = "200", description = "Status response")
    fun status(
        @Parameter(description = "Status code or value") @PathParam("status") status: String
    ): Response {
        val code = status.toIntOrNull()
        return if (code != null && code in 100..599) {
            Response.status(code).entity(status).type(MediaType.TEXT_PLAIN).build()
        } else {
            Response.status(Response.Status.OK).entity(status).type(MediaType.TEXT_PLAIN).build()
        }
    }

    @GET
    @Path("/status/coroutine/{status}")
    @Operation(summary = "Status response with coroutine", description = "Returns status using suspend function")
    @APIResponse(responseCode = "200", description = "Status response")
    suspend fun statusCoroutine(
        @Parameter(description = "Status code or value") @PathParam("status") status: String
    ): Response {
        delay(10)
        val code = status.toIntOrNull()
        return if (code != null && code in 100..599) {
            Response.status(code).entity(status).type(MediaType.TEXT_PLAIN).build()
        } else {
            Response.status(Response.Status.OK).entity(status).type(MediaType.TEXT_PLAIN).build()
        }
    }

    @GET
    @Path("/status/uni/{status}")
    @Operation(summary = "Status response with Uni", description = "Returns status using Uni")
    @APIResponse(responseCode = "200", description = "Status response")
    fun statusUni(
        @Parameter(description = "Status code or value") @PathParam("status") status: String
    ): Uni<Response> {
        val code = status.toIntOrNull()
        val response = if (code != null && code in 100..599) {
            Response.status(code).entity(status).type(MediaType.TEXT_PLAIN).build()
        } else {
            Response.status(Response.Status.OK).entity(status).type(MediaType.TEXT_PLAIN).build()
        }
        return Uni.createFrom().item(response)
    }
}
