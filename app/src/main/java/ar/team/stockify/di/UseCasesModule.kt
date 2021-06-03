package ar.team.stockify.di

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.usecases.GetFavouritesUseCase
import ar.team.stockify.usecases.GetStocksUseCase
import ar.team.stockify.usecases.GetUserUseCase
import ar.team.stockify.usecases.SetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun providesGetStocksUseCase(
        stockifyRepository: StockifyRepository
    ): GetStocksUseCase = GetStocksUseCase(stockifyRepository = stockifyRepository)

    @Provides
    fun providesGetFavouritesUseCase(
        stockifyRepository: StockifyRepository
    ):GetFavouritesUseCase=GetFavouritesUseCase(stockifyRepository = stockifyRepository)

    @Provides
    fun providesGetUserUseCase(
        stockifyRepository: StockifyRepository
    ): GetUserUseCase = GetUserUseCase(stockifyRepository = stockifyRepository)

    @Provides
    fun providesSetUserUseCase(
        stockifyRepository: StockifyRepository
    ): SetUserUseCase = SetUserUseCase(stockifyRepository = stockifyRepository)
}