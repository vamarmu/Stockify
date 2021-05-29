package ar.team.stockify.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import ar.team.stockify.data.repository.StockifyRepository
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
    fun stocksRepositoryProvider(localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ): StockifyRepository = StockifyRepository(localDataSource, remoteDataSource, apiKey)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = RemoteDataSourceImp()

    @Provides
    fun localDataSourceProvider(db:StockDatabase): LocalDataSource=LocalDataSourceImp(db)
}