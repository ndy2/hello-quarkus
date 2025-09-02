package com.ndy

import io.smallrye.mutiny.Uni
import java.util.concurrent.ConcurrentHashMap

class InMemorySmsResultRepository : SmsResultRepository {
    private val db = ConcurrentHashMap<String, SmsResult>()

    override fun create(result: SmsResult): Uni<SmsResult> {
        db[result.hash] = result
        return Uni.createFrom().item(result)
    }

    override fun read(hash: String): Uni<SmsResult?> = Uni.createFrom().item(db[hash])

    override fun update(hash: String, updated: SmsResult): Uni<SmsResult?> {
        return if (!db.containsKey(hash)) {
            Uni.createFrom().nullItem()
        } else {
            db[hash] = updated.copy(hash = hash)
            Uni.createFrom().item(db[hash])
        }
    }

    override fun delete(hash: String): Uni<Boolean> = Uni.createFrom().item(db.remove(hash) != null)

    override fun list(): Uni<List<SmsResult>> = Uni.createFrom().item(db.values.toList())
}
