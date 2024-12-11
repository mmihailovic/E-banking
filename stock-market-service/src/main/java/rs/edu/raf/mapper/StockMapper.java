package rs.edu.raf.mapper;

import org.springframework.stereotype.Component;
import rs.edu.raf.dto.StockDTO;
import rs.edu.raf.dto.StockDataDTO;
import rs.edu.raf.dto.StockHistoricalDataDTO;
import rs.edu.raf.model.Stock;
import rs.edu.raf.model.StockData;
import rs.edu.raf.model.StockHistoricalData;
import rs.edu.raf.dto.StockDailyDTO;
import rs.edu.raf.dto.StockHistoryInfoDTO;
import rs.edu.raf.dto.StockMonthlyDTO;
import rs.edu.raf.dto.StockWeeklyDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StockMapper {

    public StockDTO stockToStockDTO(Stock stock) {
        return new StockDTO(stock.getTicker(), stock.getDescription(), stock.getExchange(), stock.getLastRefresh(),
                stock.getPrice(), stock.getHigh(), stock.getLow(), stock.getChange(), stock.getVolume(), stock.getContractSize(),
                stock.getMaintenanceMargin(), stock.getChangePercent(), stock.getDollarVolume(), stock.getNominalValue(),
                stock.getInitialMarginCost(), stock.getOutstandingShares(), stock.getDividendYield(), stock.getMarketCap());
    }

    public StockHistoricalData stockWeeklyResponseToStock(StockWeeklyDTO stockWeeklyDTO) {
        StockHistoricalData stockHistoricalData = new StockHistoricalData();
        stockHistoricalData.setTicker(stockWeeklyDTO.metaData().get("2. Symbol") + "_WEEKLY");
        setHistoricalData(stockHistoricalData, stockWeeklyDTO.timeSeriesWeekly());
        return stockHistoricalData;
    }

    public StockHistoricalData stockDailyResponseToStock(StockDailyDTO stockDailyDTO) {
        StockHistoricalData stockHistoricalData = new StockHistoricalData();
        stockHistoricalData.setTicker(stockDailyDTO.metaData().get("2. Symbol") + "_DAILY");
        setHistoricalData(stockHistoricalData, stockDailyDTO.timeSeriesDaily());
        return stockHistoricalData;
    }

    public StockHistoricalData stockMonthlyResponseToStock(StockMonthlyDTO stockMonthlyDTO) {
        StockHistoricalData stockHistoricalData = new StockHistoricalData();
        stockHistoricalData.setTicker(stockMonthlyDTO.metaData().get("2. Symbol") + "_MONTHLY");
        setHistoricalData(stockHistoricalData, stockMonthlyDTO.timeSeriesMonthly());
        return stockHistoricalData;
    }

    public StockHistoricalDataDTO stockHistoricalDataToStockHistoricalDataDTO(StockHistoricalData stockHistoricalData) {
        List<StockDataDTO> stockDataDTOS = stockHistoricalData.getHistoricalData()
                .stream()
                .map(data -> new StockDataDTO(data.getDate(), data.getOpen(), data.getHigh(), data.getLow(),
                        data.getClose(), data.getVolume()))
                .toList();

        return new StockHistoricalDataDTO(stockHistoricalData.getTicker(), stockDataDTOS);
    }

    private void setHistoricalData(StockHistoricalData stockHistoricalData, Map<String, StockHistoryInfoDTO> map) {
        List<StockData> stockDataList = new ArrayList<>();

        for (Map.Entry<String, StockHistoryInfoDTO> entry: map.entrySet()) {
            StockHistoryInfoDTO stockHistoryInfoDTO = entry.getValue();

            StockData stockData = new StockData(entry.getKey(), stockHistoryInfoDTO.open(), stockHistoryInfoDTO.high(),
                    stockHistoryInfoDTO.low(), stockHistoryInfoDTO.close(), stockHistoryInfoDTO.volume());

            stockDataList.add(stockData);
        }

        stockHistoricalData.setHistoricalData(stockDataList);
    }
}
