package ar.team.stockify.data.source

import ar.team.stockify.domain.User

import ar.team.stockify.domain.Stock
interface LocalDataSource {

    suspend fun getSymbols(): List<Stock>

    suspend fun getUser():User?

    suspend fun setUser(name : String, resourceUrl:String) : User

    suspend fun removeUser()

}