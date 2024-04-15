package com.android.clix.koin.modules

import com.android.clix.data.local.AppDatabase
import com.android.clix.data.local.AppDatabase.Companion.getDatabase
import com.android.clix.data.local.CityDao
import org.koin.core.scope.Scope
import org.koin.dsl.module


val localModule = module {
    single { getDatabase(get()) }

    single { getCityDao() }
}

private fun Scope.getCityDao(): CityDao {
    return get<AppDatabase>().cityDao()
}