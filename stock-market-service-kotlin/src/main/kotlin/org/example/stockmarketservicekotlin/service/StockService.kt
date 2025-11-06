package org.example.stockmarketservicekotlin.service

import org.example.stockmarketservicekotlin.dto.StockDTO
import org.example.stockmarketservicekotlin.dto.StockHistoricalDataDTO

interface StockService {
    /**
     * Retrieves stock with specified ticker
     * @param ticker the ticker
     * @return [StockDTO] object representing stock
     */
    fun getStockByTicker(ticker: String): StockDTO

    /**
     * Retrieves daily historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     * @return [StockHistoricalDataDTO] object representing historical data
     */
    fun getDailyHistoricalDataForTicker(ticker: String): StockHistoricalDataDTO

    /**
     * Retrieves weekly historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     * @return [StockHistoricalDataDTO] object representing historical data
     */
    fun getWeeklyHistoricalDataForTicker(ticker: String): StockHistoricalDataDTO

    /**
     * Retrieves monthly historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     * @return [StockHistoricalDataDTO] object representing historical data
     */
    fun getMonthlyHistoricalDataForTicker(ticker: String): StockHistoricalDataDTO

    /**
     * Updates all stocks with data from external API
     */
    fun updateAllStocks()

    /**
     * Fetch all stocks from external API and cache in redis
     */
    fun fetchAllStocks()

    /**
     * Fetch stock with specified ticker
     * @param ticker the ticker of the stock
     */
    fun fetchStockWithTicker(ticker: String)

    /**
     * Fetch daily historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     */
    fun fetchDailyHistoricalData(ticker: String)

    /**
     * Fetch weekly historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     */
    fun fetchWeeklyHistoricalData(ticker: String)

    /**
     * Fetch daily historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     */
    fun fetchMonthlyHistoricalData(ticker: String)
}