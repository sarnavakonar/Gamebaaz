package main.database.entity

import main.util.Constants.DEVELOPERS_TABLE
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DevelopersTable : Table<DevelopersEntity>(DEVELOPERS_TABLE) {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val logo = varchar("logo").bindTo { it.logo }
    val about = varchar("about").bindTo { it.about }
    val founded = varchar("founded").bindTo { it.founded }
    val twitter = varchar("twitter").bindTo { it.twitter }
    val insta = varchar("insta").bindTo { it.insta }
    val fb = varchar("fb").bindTo { it.fb }
}

interface DevelopersEntity : Entity<DevelopersEntity> {
    companion object : Entity.Factory<DevelopersEntity>()
    val id: Int
    var name: String
    var logo: String
    val about: String
    val founded: String
    val twitter: String
    val insta: String
    val fb: String
}