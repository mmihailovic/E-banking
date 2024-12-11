package rs.edu.raf.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.edu.raf.model.credit.CreditRequest;
import rs.edu.raf.model.credit.CreditType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {
    @Mock
    private CreditRequest creditRequest;
    @Mock
    private CreditType creditType;
    @InjectMocks
    private CreditServiceImpl creditService;

    @Test
    void calculateInstallmentAmountForCredit_Success1() {
        // Arrange
        when(creditRequest.getLoanTerm()).thenReturn(24); // 2 godine
        when(creditRequest.getLoanAmount()).thenReturn(BigDecimal.valueOf(10000)); // 10,000
        when(creditRequest.getCreditType()).thenReturn(creditType);
        when(creditType.getNominalInterestRate()).thenReturn(BigDecimal.valueOf(5)); // 5% nominalna kamata

        // Act
        BigDecimal monthlyInstallment = creditService.calculateInstallmentAmountForCredit(creditRequest);

        // Assert
        assertNotNull(monthlyInstallment);
        assertEquals(BigDecimal.valueOf(438.71), monthlyInstallment); // Očekivana rata
    }

    @Test
    void calculateInstallmentAmountForCredit_Success() {

        when(creditRequest.getLoanTerm()).thenReturn(12); // 1 godina
        when(creditRequest.getLoanAmount()).thenReturn(BigDecimal.valueOf(12000)); // 12,000
        when(creditRequest.getCreditType()).thenReturn(creditType);
        when(creditType.getNominalInterestRate()).thenReturn(BigDecimal.ZERO); // 0% kamata

        // Act
        BigDecimal monthlyInstallment = creditService.calculateInstallmentAmountForCredit(creditRequest);

        // Assert
        assertNotNull(monthlyInstallment);
        assertEquals(BigDecimal.valueOf(1000.00), monthlyInstallment); // Bez kamate, rata je jednostavno podeljena
    }
}