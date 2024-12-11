package rs.edu.raf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.*;
import rs.edu.raf.service.OrderService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
@CrossOrigin("*")
@Tag(name = "Order controller", description = "Manage orders")
@SecurityRequirement(name = "jwt")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/market")
    @Operation(description = "Create market order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid MarketOrderCreateDTO marketOrderCreateDTO) {
        return new ResponseEntity<>(orderService.createMarketOrder(marketOrderCreateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/limit")
    @Operation(description = "Create limit order")
    public ResponseEntity<OrderDTO> createLimitOrder(@RequestBody @Valid LimitOrderCreateDTO limitOrderCreateDTO) {
        return new ResponseEntity<>(orderService.createLimitOrder(limitOrderCreateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/stop")
    @Operation(description = "Create stop order")
    public ResponseEntity<OrderDTO> createStopOrder(@RequestBody @Valid StopOrderCreateDTO stopOrderCreateDTO) {
        return new ResponseEntity<>(orderService.createStopOrder(stopOrderCreateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/stop-limit")
    @Operation(description = "Create stop limit order")
    public ResponseEntity<OrderDTO> createStopLimitOrder(@RequestBody @Valid StopLimitOrderCreateDTO stopLimitOrderCreateDTO) {
        return new ResponseEntity<>(orderService.createStopLimitOrder(stopLimitOrderCreateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/all-or-none")
    @Operation(description = "Create all or none order")
    public ResponseEntity<OrderDTO> createAllOrNoneOrder(@RequestBody @Valid AllOrNoneOrderCreateDTO allOrNoneOrderCreateDTO) {
        return new ResponseEntity<>(orderService.createAllOrNoneOrder(allOrNoneOrderCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(description = "Get all orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }
}
