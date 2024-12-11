package rs.edu.raf.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.edu.raf.dto.*;
import rs.edu.raf.exceptions.StockDoesntExist;
import rs.edu.raf.mapper.StockMapper;
import rs.edu.raf.model.Stock;
import rs.edu.raf.repository.StockHistoricalDataRepository;
import rs.edu.raf.repository.StockRepository;
import rs.edu.raf.service.StockService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final StockHistoricalDataRepository stockHistoricalDataRepository;
    private final StockMapper stockMapper;
    private final RestTemplate alphaVantageRestTemplate;
//    @Value("${api.key}")
//    private static String API_KEY;
    private static final String API_KEY = "demo";

    @Override
    public StockDTO getStockByTicker(String ticker) {
        return stockMapper.stockToStockDTO(stockRepository.findById(ticker)
                .orElseThrow(() -> new StockDoesntExist(ticker)));
    }

    @Override
    public StockHistoricalDataDTO getDailyHistoricalDataForTicker(String ticker) {
        return stockMapper.stockHistoricalDataToStockHistoricalDataDTO(
                stockHistoricalDataRepository.findById(ticker+"_DAILY")
                        .orElseThrow(() -> new StockDoesntExist(ticker)));
    }

    @Override
    public StockHistoricalDataDTO getWeeklyHistoricalDataForTicker(String ticker) {
        return stockMapper.stockHistoricalDataToStockHistoricalDataDTO(
                stockHistoricalDataRepository.findById(ticker+"_WEEKLY")
                        .orElseThrow(() -> new StockDoesntExist(ticker)));
    }

    @Override
    public StockHistoricalDataDTO getMonthlyHistoricalDataForTicker(String ticker) {
        return stockMapper.stockHistoricalDataToStockHistoricalDataDTO(
                stockHistoricalDataRepository.findById(ticker+"_MONTHLY")
                        .orElseThrow(() -> new StockDoesntExist(ticker)));
    }

    @Override
    public void updateAllStocks() {
        for(Stock stock: stockRepository.findAll()) {
            fetchStockWithTicker(stock.getTicker());
        }
    }

    @Override
    public void fetchAllStocks() {
        try {
            byte[] response = alphaVantageRestTemplate.exchange("?function=LISTING_STATUS&apikey=" + API_KEY,
                HttpMethod.GET, null, byte[].class).getBody();

            InputStream inputStream = new ByteArrayInputStream(response);

            List<String> stocks = getStocksFromFile(inputStream);

            for(String ticker: stocks) {
                fetchStockWithTicker(ticker);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetchStockWithTicker(String ticker) {
        StockOverviewDTO stockOverview = alphaVantageRestTemplate.exchange("?function=OVERVIEW&symbol="
                        + ticker + "&apikey=" + API_KEY, HttpMethod.GET, null, StockOverviewDTO.class)
                .getBody();

        StockGlobalQuoteDTO stockGlobalQuote = alphaVantageRestTemplate.exchange("?function=GLOBAL_QUOTE&symbol="
                        + ticker + "&apikey=" + API_KEY, HttpMethod.GET, null, StockGlobalQuoteDTO.class)
                .getBody();

        if(stockOverview == null || stockGlobalQuote == null) {
            return;
        }

        GlobalQuoteDTO globalQuoteDTO = stockGlobalQuote.globalQuote();

        if(globalQuoteDTO == null) {
            return;
        }

        Stock stock = new Stock(ticker, stockOverview.Description(), stockOverview.Exchange(),
                System.currentTimeMillis(), globalQuoteDTO.price(), globalQuoteDTO.high(), globalQuoteDTO.low(),
                globalQuoteDTO.change(), globalQuoteDTO.volume(), stockOverview.SharesOutstanding(),
                stockOverview.DividendYield());

        stockRepository.save(stock);
    }

    @Override
    public void fetchDailyHistoricalData(String ticker) {
        StockDailyDTO response = alphaVantageRestTemplate.exchange("?function=TIME_SERIES_DAILY&symbol="
                + ticker + "&apikey=" + API_KEY, HttpMethod.GET, null, StockDailyDTO.class).getBody();

        stockHistoricalDataRepository.save(stockMapper.stockDailyResponseToStock(response));
    }

    @Override
    public void fetchWeeklyHistoricalData(String ticker) {
        StockWeeklyDTO response = alphaVantageRestTemplate.exchange("?function=TIME_SERIES_WEEKLY&symbol="
                + ticker + "&apikey=" + API_KEY, HttpMethod.GET, null, StockWeeklyDTO.class).getBody();

        stockHistoricalDataRepository.save(stockMapper.stockWeeklyResponseToStock(response));
    }

    @Override
    public void fetchMonthlyHistoricalData(String ticker) {
        StockMonthlyDTO response = alphaVantageRestTemplate.exchange("?function=TIME_SERIES_MONTHLY&symbol="
                + ticker + "&apikey=" + API_KEY, HttpMethod.GET, null, StockMonthlyDTO.class).getBody();

        stockHistoricalDataRepository.save(stockMapper.stockMonthlyResponseToStock(response));
    }

    private List<String> getStocksFromFile(InputStream inputStream) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        List<String> stocks = new ArrayList<>();

        reader.skip(1);

        for (String[] row: reader.readAll()) {
            stocks.add(row[0]);
        }
        return stocks;
    }
}
