package org.example.stockmarketservicekotlin.controller

import org.example.stockmarketservicekotlin.dto.StockDTO
import org.example.stockmarketservicekotlin.dto.StockHistoricalDataDTO
import org.example.stockmarketservicekotlin.service.StockService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/stocks")
class StockController(private val stockService: StockService) {
    @GetMapping("/{ticker}")
    fun getStockWithTicker(@PathVariable("ticker") ticker: String): ResponseEntity<StockDTO> {
        return ResponseEntity<StockDTO>(stockService.getStockByTicker(ticker), HttpStatus.OK)
    }

    @GetMapping("/daily/{ticker}")
    fun getDailyHistoricalDataForTicker(@PathVariable("ticker") ticker: String): ResponseEntity<StockHistoricalDataDTO> {
        return ResponseEntity<StockHistoricalDataDTO>(
            stockService.getDailyHistoricalDataForTicker(ticker),
            HttpStatus.OK
        )
    }

    @GetMapping("/weekly/{ticker}")
    fun getWeeklyHistoricalDataForTicker(@PathVariable("ticker") ticker: String): ResponseEntity<StockHistoricalDataDTO> {
        return ResponseEntity<StockHistoricalDataDTO>(
            stockService.getWeeklyHistoricalDataForTicker(ticker),
            HttpStatus.OK
        )
    }

    @GetMapping("/monthly/{ticker}")
    fun getMonthlyHistoricalDataForTicker(@PathVariable("ticker") ticker: String): ResponseEntity<StockHistoricalDataDTO> {
        return ResponseEntity<StockHistoricalDataDTO>(
            stockService.getMonthlyHistoricalDataForTicker(ticker),
            HttpStatus.OK
        )
    }

    @PutMapping("/fetch/{ticker}")
    fun fetchStockWithTicker(@PathVariable("ticker") ticker: String): ResponseEntity<*> {
        stockService.fetchStockWithTicker(ticker)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/fetch/all")
    fun fetchAllStocks(): ResponseEntity<*> {
        stockService.fetchAllStocks()
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/update/all")
    fun updateAllStocks(): ResponseEntity<*> {
        stockService.updateAllStocks()
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/daily/{ticker}")
    fun fetchDailyHistoricalDataForStockWithTicker(@PathVariable("ticker") ticker: String): ResponseEntity<*> {
        stockService.fetchDailyHistoricalData(ticker)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/weekly/{ticker}")
    fun fetchWeeklyHistoricalForDataStockWithTicker(@PathVariable("ticker") ticker: String): ResponseEntity<*> {
        stockService.fetchWeeklyHistoricalData(ticker)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/monthly/{ticker}")
    fun fetchMonthlyHistoricalDataForStockWithTicker(@PathVariable("ticker") ticker: String): ResponseEntity<*> {
        stockService.fetchMonthlyHistoricalData(ticker)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}