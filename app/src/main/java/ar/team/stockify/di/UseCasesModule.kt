package ar.team.stockify.di

import ar.team.stockify.data.repository.FavouritesRepository
import ar.team.stockify.data.repository.StocksRepository
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
        stocksRepository: StocksRepository
    ): GetStocksUseCase = GetStocksUseCase(stocksRepository)

    @Provides
    fun providesGetFavouritesUseCase(
        favouritesRepository: FavouritesRepository
    ):GetFavouritesUseCase=GetFavouritesUseCase(favouritesRepository)
}