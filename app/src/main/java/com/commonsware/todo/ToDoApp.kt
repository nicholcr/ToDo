package com.commonsware.todo

import android.app.Application
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.ui.SingleModelMotor
import com.commonsware.todo.ui.roster.RosterMotor
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ToDoApp : Application() {
  private val koinModule = module {
    single { ToDoRepository() }
    viewModel { RosterMotor(get()) }
    viewModel { (modelId: String) -> SingleModelMotor(get(), modelId) }
  }

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      modules(koinModule)
    }
  }
}