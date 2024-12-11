package rs.edu.raf.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.StockDTO;
import rs.edu.raf.dto.StockHistoricalDataDTO;
import rs.edu.raf.service.StockService;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
@CrossOrigin("*")
public class StockController {
    private StockService stockService;

    @GetMapping("/{ticker}")
    public ResponseEntity<StockDTO> getStockWithTicker(@PathVariable("ticker") String ticker) {
        return new ResponseEntity<>(stockService.getStockByTicker(ticker), HttpStatus.OK);
    }

    @GetMapping("/daily/{ticker}")
    public ResponseEntity<StockHistoricalDataDTO> getDailyHistoricalDataForTicker(@PathVariable("ticker") String ticker) {
        return new ResponseEntity<>(stockService.getDailyHistoricalDataForTicker(ticker), HttpStatus.OK);
    }

    @GetMapping("/weekly/{ticker}")
    public ResponseEntity<StockHistoricalDataDTO> getWeeklyHistoricalDataForTicker(@PathVariable("ticker") String ticker) {
        return new ResponseEntity<>(stockService.getWeeklyHistoricalDataForTicker(ticker), HttpStatus.OK);
    }

    @GetMapping("/monthly/{ticker}")
    public ResponseEntity<StockHistoricalDataDTO> getMonthlyHistoricalDataForTicker(@PathVariable("ticker") String ticker) {
        return new ResponseEntity<>(stockService.getMonthlyHistoricalDataForTicker(ticker), HttpStatus.OK);
    }

    @PutMapping("/fetch/{ticker}")
    public ResponseEntity<?> fetchStockWithTicker(@PathVariable("ticker") String ticker) {
        stockService.fetchStockWithTicker(ticker);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/fetch/all")
    public ResponseEntity<?> fetchAllStocks() {
        stockService.fetchAllStocks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/all")
    public ResponseEntity<?> updateAllStocks() {
        stockService.updateAllStocks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/daily/{ticker}")
    public ResponseEntity<?> fetchDailyHistoricalDataForStockWithTicker(@PathVariable("ticker") String ticker) {
        stockService.fetchDailyHistoricalData(ticker);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/weekly/{ticker}")
    public ResponseEntity<?> fetchWeeklyHistoricalForDataStockWithTicker(@PathVariable("ticker") String ticker) {
        stockService.fetchWeeklyHistoricalData(ticker);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/monthly/{ticker}")
    public ResponseEntity<?> fetchMonthlyHistoricalDataForStockWithTicker(@PathVariable("ticker") String ticker) {
        stockService.fetchMonthlyHistoricalData(ticker);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
