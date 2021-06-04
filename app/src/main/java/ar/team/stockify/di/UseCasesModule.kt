package ar.team.stockify.di

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.usecases.AddRemoveFavUseCase
import ar.team.stockify.usecases.GetFavouritesUseCase
import ar.team.stockify.usecases.GetStocksUseCase
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
    ): GetStocksUseCase = GetStocksUseCase(stockifyRepository)

    @Provides
    fun providesGetFavouritesUseCase(
        stockifyRepository: StockifyRepository
    ):GetFavouritesUseCase=GetFavouritesUseCase(stockifyRepository = stockifyRepository)

    @Provides
    fun providesAddRemoveFavUseCase(
        stockifyRepository: StockifyRepository
    ): AddRemoveFavUseCase = AddRemoveFavUseCase(stockifyRepository= stockifyRepository)
}