package org.example.model.common

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.Instant

@MappedSuperclass
interface BaseModel {
    val id: Long?
    val createdAt: Instant?
    val updatedAt: Instant?
    val deletedAt: Instant?
}