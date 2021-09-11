package main.database.entity

import main.util.Constants
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UsersTable : Table<UsersEntity>(Constants.USERS_TABLE) {
    val id = int("id").primaryKey().bindTo { it.id }
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
}

interface UsersEntity : Entity<UsersEntity> {
    companion object : Entity.Factory<UsersEntity>()
    val id: Int
    var username: String
    var password: String
}