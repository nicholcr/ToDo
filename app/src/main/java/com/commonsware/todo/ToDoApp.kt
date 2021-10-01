package com.commonsware.todo

import android.app.Application
import com.commonsware.todo.repo.ToDoDatabase
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.ui.SingleModelMotor
import com.commonsware.todo.ui.roster.RosterMotor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ToDoApp : Application() {
  private val koinModule = module {
    single {
      ToDoRepository(
        get<ToDoDatabase>().todoStore(),
        get(named("appScope"))
      )
    }
    single { ToDoDatabase.newInstance(androidContext()) }
    single(named("appScope")) { CoroutineScope(SupervisorJob()) }
    viewModel { RosterMotor(get()) }
    viewModel { (modelId: String) -> SingleModelMotor(get(), modelId) }
  }

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      androidContext(this@ToDoApp)
      modules(koinModule)
    }
  }
}