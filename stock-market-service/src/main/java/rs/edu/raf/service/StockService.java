package rs.edu.raf.service;

import rs.edu.raf.dto.StockDTO;
import rs.edu.raf.dto.StockHistoricalDataDTO;

public interface StockService {
    /**
     * Retrieves stock with specified ticker
     * @param ticker the ticker
     * @return {@link StockDTO} object representing stock
     */
    StockDTO getStockByTicker(String ticker);

    /**
     * Retrieves daily historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     * @return {@link StockHistoricalDataDTO} object representing historical data
     */
    StockHistoricalDataDTO getDailyHistoricalDataForTicker(String ticker);

    /**
     * Retrieves weekly historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     * @return {@link StockHistoricalDataDTO} object representing historical data
     */
    StockHistoricalDataDTO getWeeklyHistoricalDataForTicker(String ticker);

    /**
     * Retrieves monthly historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     * @return {@link StockHistoricalDataDTO} object representing historical data
     */
    StockHistoricalDataDTO getMonthlyHistoricalDataForTicker(String ticker);

    /**
     * Updates all stocks with data from external API
     */
    void updateAllStocks();

    /**
     * Fetch all stocks from external API and cache in redis
     */
    void fetchAllStocks();

    /**
     * Fetch stock with specified ticker
     * @param ticker the ticker of the stock
     */
    void fetchStockWithTicker(String ticker);

    /**
     * Fetch daily historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     */
    void fetchDailyHistoricalData(String ticker);

    /**
     * Fetch weekly historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     */
    void fetchWeeklyHistoricalData(String ticker);

    /**
     * Fetch daily historical data for stock with specified ticker
     * @param ticker the ticker of the stock
     */
    void fetchMonthlyHistoricalData(String ticker);
}
