package com.ndy

import io.smallrye.mutiny.Uni

interface SmsResultRepository {
    fun create(result: SmsResult): Uni<SmsResult>
    fun read(hash: String): Uni<SmsResult?>
    fun update(hash: String, updated: SmsResult): Uni<SmsResult?>
    fun delete(hash: String): Uni<Boolean>
    fun list(): Uni<List<SmsResult>>
}
