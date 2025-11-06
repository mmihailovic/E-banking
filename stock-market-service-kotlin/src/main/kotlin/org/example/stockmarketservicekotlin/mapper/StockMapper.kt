package org.example.stockmarketservicekotlin.mapper

import org.example.stockmarketservicekotlin.dto.*
import org.example.stockmarketservicekotlin.model.Stock
import org.example.stockmarketservicekotlin.model.StockData
import org.example.stockmarketservicekotlin.model.StockHistoricalData
import org.springframework.stereotype.Component

@Component
class StockMapper {
    fun stockToStockDTO(stock: Stock): StockDTO {
        return StockDTO(
            stock.ticker,
            stock.description,
            stock.exchange,
            stock.lastRefresh,
            stock.price,
            stock.high,
            stock.low,
            stock.change,
            stock.volume,
            stock.contractSize,
            stock.maintenanceMargin,
            stock.changePercent,
            stock.dollarVolume,
            stock.nominalValue,
            stock.initialMarginCost,
            stock.outstandingShares,
            stock.dividendYield,
            stock.marketCap
        )
    }

    fun stockWeeklyResponseToStock(stockWeeklyDTO: StockWeeklyDTO): StockHistoricalData {
        return setData(stockWeeklyDTO.metaData["2. Symbol"] + "_WEEKLY", stockWeeklyDTO.timeSeriesWeekly)
    }

    fun stockDailyResponseToStock(stockDailyDTO: StockDailyDTO): StockHistoricalData {
        return setData(stockDailyDTO.metaData["2. Symbol"] + "_DAILY", stockDailyDTO.timeSeriesDaily)
    }

    fun stockMonthlyResponseToStock(stockMonthlyDTO: StockMonthlyDTO): StockHistoricalData {
        return setData(stockMonthlyDTO.metaData["2. Symbol"] + "_MONTHLY", stockMonthlyDTO.timeSeriesMonthly)
    }

    fun stockHistoricalDataToStockHistoricalDataDTO(stockHistoricalData: StockHistoricalData): StockHistoricalDataDTO {
        val stockDataDTOS: List<StockDataDTO> = stockHistoricalData.historicalData.stream().map { data ->
                StockDataDTO(
                    data.date, data.open, data.high, data.low, data.close, data.volume
                )
            }.toList()

        return StockHistoricalDataDTO(stockHistoricalData.ticker, stockDataDTOS)
    }

    private fun setData(ticker: String, map: Map<String, StockHistoryInfoDTO>): StockHistoricalData {
        val stockDataList: MutableList<StockData> = ArrayList<StockData>()

        for ((key, stockHistoryInfoDTO) in map) {
            val stockData: StockData = StockData(
                key,
                stockHistoryInfoDTO.open,
                stockHistoryInfoDTO.high,
                stockHistoryInfoDTO.low,
                stockHistoryInfoDTO.close,
                stockHistoryInfoDTO.volume
            )

            stockDataList.add(stockData)
        }

        return StockHistoricalData(ticker, stockDataList)
    }
}