package com.ndy

import kotlinx.serialization.Serializable
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Serializable
@Schema(description = "SMS Result entity")
data class SmsResult(
    @Schema(description = "Unique hash identifier", example = "abc123")
    val hash: String,
    @Schema(description = "SMS message content", example = "Hello World")
    val message: String
)
