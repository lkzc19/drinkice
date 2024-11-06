package org.example.model

import org.babyfish.jimmer.Immutable

@Immutable
interface FileInfoModel {

    val name: String
    val size: Long
    val path: String
}