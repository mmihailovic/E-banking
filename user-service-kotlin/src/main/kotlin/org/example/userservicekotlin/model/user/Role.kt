package org.example.userservicekotlin.model.user

import jakarta.persistence.*

@Entity
class Role (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    @ManyToMany(mappedBy = "roles")
    val users: List<User>
) {

    constructor(name: String): this(0, name, emptyList())

    override fun toString(): String {
        return name
    }
}
