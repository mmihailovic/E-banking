package org.example.stockmarketservicekotlin.repository

import org.example.stockmarketservicekotlin.model.StockHistoricalData
import org.springframework.data.repository.CrudRepository

interface StockHistoricalDataRepository:CrudRepository<StockHistoricalData, String> {
}