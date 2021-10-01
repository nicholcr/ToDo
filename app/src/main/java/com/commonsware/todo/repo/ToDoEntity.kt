package com.commonsware.todo.repo

import androidx.room.*
import java.time.Instant
import java.util.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "todos", indices = [Index(value = ["id"])])
data class ToDoEntity(
    val description: String,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val notes: String = "",
    val createdOn: Instant = Instant.now(),
    val isCompleted: Boolean = false
) {
    @Dao
    interface Store {
        @Query("SELECT * FROM todos ORDER BY description")
        fun all(): Flow<List<ToDoEntity>>

        @Query("SELECT * FROM todos WHERE id = :modelId")
        fun find(modelId: String?): Flow<ToDoEntity?>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun save(vararg entities: ToDoEntity)

        @Delete
        suspend fun delete(vararg entities: ToDoEntity)
    }
}