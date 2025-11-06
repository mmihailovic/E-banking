package org.example.stockmarketservicekotlin.service.impl

import com.opencsv.exceptions.CsvException
import com.opencsv.CSVReader
import org.example.stockmarketservicekotlin.dto.*
import org.example.stockmarketservicekotlin.exceptions.StockDoesntExistException
import org.example.stockmarketservicekotlin.mapper.StockMapper
import org.example.stockmarketservicekotlin.model.Stock
import org.example.stockmarketservicekotlin.repository.StockHistoricalDataRepository
import org.example.stockmarketservicekotlin.repository.StockRepository
import org.example.stockmarketservicekotlin.service.StockService
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@Service
class StockServiceImpl(
    private val stockRepository: StockRepository,
    private val stockHistoricalDataRepository: StockHistoricalDataRepository,
    private val stockMapper: StockMapper,
    private val alphaVantageRestTemplate: RestTemplate,
//    @Value("${api.key}")
//    private static String API_KEY;
) : StockService {
    private val API_KEY: String = "demo"
    override fun getStockByTicker(ticker: String): StockDTO {
        return stockMapper.stockToStockDTO(
            stockRepository.findById(ticker).orElseThrow { StockDoesntExistException(ticker) })
    }

    override fun getDailyHistoricalDataForTicker(ticker: String): StockHistoricalDataDTO {
        return stockMapper.stockHistoricalDataToStockHistoricalDataDTO(
            stockHistoricalDataRepository.findById(ticker + "_DAILY").orElseThrow { StockDoesntExistException(ticker) })
    }

    override fun getWeeklyHistoricalDataForTicker(ticker: String): StockHistoricalDataDTO {
        return stockMapper.stockHistoricalDataToStockHistoricalDataDTO(
            stockHistoricalDataRepository.findById(ticker + "_WEEKLY")
                .orElseThrow { StockDoesntExistException(ticker) })
    }

    override fun getMonthlyHistoricalDataForTicker(ticker: String): StockHistoricalDataDTO {
        return stockMapper.stockHistoricalDataToStockHistoricalDataDTO(
            stockHistoricalDataRepository.findById(ticker + "_MONTHLY")
                .orElseThrow { StockDoesntExistException(ticker) })
    }

    override fun updateAllStocks() {
        for (stock in stockRepository.findAll()) {
            fetchStockWithTicker(stock.ticker)
        }
    }

    override fun fetchAllStocks() {
        try {
            val response = alphaVantageRestTemplate.exchange(
                "?function=LISTING_STATUS&apikey=$API_KEY", HttpMethod.GET, null, ByteArray::class.java
            ).body

            val inputStream: InputStream = ByteArrayInputStream(response)

            val stocks = getStocksFromFile(inputStream)

            for (ticker in stocks) {
                fetchStockWithTicker(ticker)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: CsvException) {
            throw RuntimeException(e)
        }
    }

    override fun fetchStockWithTicker(ticker: String) {
        val stockOverview: StockOverviewDTO? = alphaVantageRestTemplate.exchange(
            ("?function=OVERVIEW&symbol=$ticker&apikey=$API_KEY"),
            HttpMethod.GET,
            null,
            StockOverviewDTO::class.java
        ).body

        val stockGlobalQuote: StockGlobalQuoteDTO? = alphaVantageRestTemplate.exchange(
            ("?function=GLOBAL_QUOTE&symbol=$ticker&apikey=$API_KEY"),
            HttpMethod.GET,
            null,
            StockGlobalQuoteDTO::class.java
        ).body

        if (stockOverview == null || stockGlobalQuote == null) {
            return
        }

        val globalQuoteDTO: GlobalQuoteDTO = stockGlobalQuote.globalQuote
        val maintenanceMargin = globalQuoteDTO.price / 2

        val stock = Stock(
            ticker,
            stockOverview.Description,
            stockOverview.Exchange,
            System.currentTimeMillis(),
            globalQuoteDTO.price,
            globalQuoteDTO.high,
            globalQuoteDTO.low,
            globalQuoteDTO.change,
            globalQuoteDTO.volume,
            1,
            maintenanceMargin,
            (100 * globalQuoteDTO.change) / (globalQuoteDTO.price - globalQuoteDTO.change),
            globalQuoteDTO.volume * globalQuoteDTO.price,
            globalQuoteDTO.price,
            maintenanceMargin * 1.1,
            stockOverview.SharesOutstanding,
            stockOverview.DividendYield,
            stockOverview.SharesOutstanding * globalQuoteDTO.price
        )

        stockRepository.save(stock)
    }

    override fun fetchDailyHistoricalData(ticker: String) {
        val response: StockDailyDTO? = alphaVantageRestTemplate.exchange(
            ("?function=TIME_SERIES_DAILY&symbol=$ticker&apikey=$API_KEY"),
            HttpMethod.GET,
            null,
            StockDailyDTO::class.java
        ).body

        if(response == null)
            return

        stockHistoricalDataRepository.save(stockMapper.stockDailyResponseToStock(response))
    }

    override fun fetchWeeklyHistoricalData(ticker: String) {
        val response: StockWeeklyDTO? = alphaVantageRestTemplate.exchange(
            ("?function=TIME_SERIES_WEEKLY&symbol=$ticker&apikey=$API_KEY"),
            HttpMethod.GET,
            null,
            StockWeeklyDTO::class.java
        ).body

        if(response == null)
            return

        stockHistoricalDataRepository.save(stockMapper.stockWeeklyResponseToStock(response))
    }

    override fun fetchMonthlyHistoricalData(ticker: String) {
        val response: StockMonthlyDTO? = alphaVantageRestTemplate.exchange(
            ("?function=TIME_SERIES_MONTHLY&symbol=$ticker&apikey=$API_KEY"),
            HttpMethod.GET,
            null,
            StockMonthlyDTO::class.java
        ).body

        if(response == null)
            return

        stockHistoricalDataRepository.save(stockMapper.stockMonthlyResponseToStock(response))
    }

    @Throws(IOException::class, CsvException::class)
    private fun getStocksFromFile(inputStream: InputStream): List<String> {
        val reader: CSVReader = CSVReader(InputStreamReader(inputStream))
        val stocks: MutableList<String> = ArrayList()

        reader.skip(1)

        for (row in reader.readAll()) {
            stocks.add(row[0])
        }
        return stocks
    }
}