package ar.team.stockify.di

import android.app.Application
import androidx.room.Room
import ar.team.stockify.data.repository.FavouritesRepository
import ar.team.stockify.data.repository.StocksRepository
import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.database.LocalDataSourceImp
import ar.team.stockify.database.StockDatabase
import ar.team.stockify.network.RemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        StockDatabase::class.java,
        "stock-db"
    ).build()

    @Provides
    fun stocksRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ): StocksRepository = StocksRepository(remoteDataSource, apiKey)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = RemoteDataSourceImp()

    @Provides
    fun favouritesRepositoryProvider(
        localDataSource: LocalDataSource
    ):FavouritesRepository= FavouritesRepository(localDataSource)

    @Provides
    fun localDataSourceProvider(db:StockDatabase): LocalDataSource=LocalDataSourceImp(db)
}