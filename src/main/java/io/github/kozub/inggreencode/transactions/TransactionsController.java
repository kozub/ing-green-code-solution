package io.github.kozub.inggreencode.transactions;

import io.github.kozub.inggreencode.generated.api.TransactionApi;
import io.github.kozub.inggreencode.generated.model.Account;
import io.github.kozub.inggreencode.generated.model.Transaction;
import jakarta.inject.Inject;

import java.util.List;

class TransactionsController implements TransactionApi {

    @Inject
    TransactionReportGenerator reportProvider;

    @Override
    public List<Account> report(List<Transaction> transaction) {
        return reportProvider.generateReport(transaction);
    }
}
