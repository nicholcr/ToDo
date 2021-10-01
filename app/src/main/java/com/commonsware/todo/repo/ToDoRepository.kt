package com.commonsware.todo.repo

import kotlinx.coroutines.CoroutineScope

class ToDoRepository(
  private val store: ToDoEntity.Store,
  private val appScope: CoroutineScope
) {
  var items = emptyList<ToDoModel>()

  fun save(model: ToDoModel) {
    items = if (items.any { it.id == model.id }) {
      items.map { if (it.id == model.id) model else it }
    } else {
      items + model
    }
  }

  fun find(modelId: String?) = items.find { it.id == modelId }

  fun delete(model: ToDoModel) {
    items = items.filter { it.id != model.id }
  }
}