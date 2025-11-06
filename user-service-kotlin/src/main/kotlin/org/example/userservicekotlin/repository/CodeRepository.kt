package org.example.userservicekotlin.repository

import org.example.userservicekotlin.model.code.Code
import org.example.userservicekotlin.model.code.CodeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CodeRepository : JpaRepository<Code, Long> {
    fun findByUserEmailAndCodeType(email: String, codeType: CodeType): Optional<Code>
}