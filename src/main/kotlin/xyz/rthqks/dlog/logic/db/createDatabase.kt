package xyz.rthqks.dlog.logic.db

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.runBlocking
import xyz.rthqks.dlog.db.Database
import java.io.File

fun createDatabase(): Database {
    val fileName = "build/settings.db"
    val file = File(fileName)
    val driver = JdbcSqliteDriver("jdbc:sqlite:$fileName")
    if (!file.exists()) {
        println("creating db file")
        file.createNewFile()
        runBlocking {
            Database.Schema.create(driver).await()
        }
    }
    return Database(driver)
}