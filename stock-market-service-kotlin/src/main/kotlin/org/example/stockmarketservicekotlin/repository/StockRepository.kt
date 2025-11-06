package org.example.stockmarketservicekotlin.repository

import org.example.stockmarketservicekotlin.model.Stock
import org.springframework.data.repository.CrudRepository

interface StockRepository: CrudRepository<Stock, String> {
}