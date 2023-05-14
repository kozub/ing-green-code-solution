package io.github.kozub.inggreencode.transactions;

import io.github.kozub.inggreencode.generated.api.TransactionApi;
import io.github.kozub.inggreencode.generated.model.Transaction;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

class TransactionsController implements TransactionApi {

    @Inject
    TransactionReportGenerator reportProvider;

    @Override
    public Response report(List<Transaction> transaction) {
        if (transaction == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(reportProvider.generateReport(transaction)).build();
    }
}
