package ar.team.stockify.di

import android.app.Application
import android.content.SharedPreferences
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
    fun databaseProvider(app: Application):StockDatabase = Room.databaseBuilder(
        app,
        StockDatabase::class.java,
        "stock-db"
    )
    .fallbackToDestructiveMigration()
    .build()


    @Provides
    @Singleton
    fun preferencesProvider (app:Application) : SharedPreferences = app.getSharedPreferences("UserPreferences",AppCompatActivity.MODE_PRIVATE)


    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = RemoteDataSourceImp()

    @Provides
    fun localDataSourceProvider(db:StockDatabase , preferences: SharedPreferences): LocalDataSource=LocalDataSourceImp(db,preferences)

    @Provides
    fun stocksRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ): StockifyRepository = StockifyRepository(localDataSource, remoteDataSource, apiKey)

}