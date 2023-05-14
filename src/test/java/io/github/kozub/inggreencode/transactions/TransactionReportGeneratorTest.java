package io.github.kozub.inggreencode.transactions;

import io.github.kozub.inggreencode.generated.model.Account;
import io.github.kozub.inggreencode.generated.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionReportGeneratorTest {

    private final TransactionReportGenerator generator = new TransactionReportGenerator();

    @Test
    void shouldSumBalanceAndSortResults() {
        var transactions = List.of(
                new Transaction()
                        .debitAccount("32309111922661937852684864")
                        .creditAccount("06105023389842834748547303")
                        .amount(BigDecimal.valueOf(10.90)),
                new Transaction()
                        .debitAccount("31074318698137062235845814")
                        .creditAccount("66105036543749403346524547")
                        .amount(BigDecimal.valueOf(200.90)),
                new Transaction()
                        .debitAccount("66105036543749403346524547")
                        .creditAccount("32309111922661937852684864")
                        .amount(BigDecimal.valueOf(50.10)));

        List<Account> result = generator.generateReport(transactions);
        assertEquals(4, result.size());

        assertEquals("06105023389842834748547303", result.get(0).getAccount());
        assertEquals(0, result.get(0).getDebitCount());
        assertEquals(1, result.get(0).getCreditCount());
        assertEquals(BigDecimal.valueOf(10.90), result.get(0).getBalance());

        assertEquals("31074318698137062235845814", result.get(1).getAccount());
        assertEquals(1, result.get(1).getDebitCount());
        assertEquals(0, result.get(1).getCreditCount());
        assertEquals(BigDecimal.valueOf(-200.90), result.get(1).getBalance());

        assertEquals("32309111922661937852684864", result.get(2).getAccount());
        assertEquals(1, result.get(2).getDebitCount());
        assertEquals(1, result.get(2).getCreditCount());
        assertEquals(BigDecimal.valueOf(39.20), result.get(2).getBalance());

        assertEquals("66105036543749403346524547", result.get(3).getAccount());
        assertEquals(1, result.get(3).getDebitCount());
        assertEquals(1, result.get(3).getCreditCount());
        assertEquals(BigDecimal.valueOf(150.80), result.get(3).getBalance());
    }

}