package ar.team.stockify.di

import ar.team.stockify.storage.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun preferenceHelperProvider() = PreferenceHelper
}