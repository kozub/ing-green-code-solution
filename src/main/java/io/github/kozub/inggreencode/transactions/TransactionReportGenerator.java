package io.github.kozub.inggreencode.transactions;



import io.github.kozub.inggreencode.generated.model.Account;
import io.github.kozub.inggreencode.generated.model.Transaction;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
class TransactionReportGenerator {

    public List<Account> generateReport(List<Transaction> transactions) {
        Map<String, Account> accountIdToAccount = new HashMap<>();

        // Naive implementation
        for (Transaction transaction : transactions) {
            Account debitAccount = accountIdToAccount.get(transaction.getDebitAccount());

            if (debitAccount != null) {
                debitAccount.debitCount(debitAccount.getDebitCount() + 1);
                debitAccount.balance(debitAccount.getBalance().subtract(transaction.getAmount()));
            } else {
                debitAccount = createAccount(transaction.getDebitAccount(), 1, 0, transaction.getAmount().negate());
                accountIdToAccount.put(transaction.getDebitAccount(), debitAccount);
            }


            Account creditAccount = accountIdToAccount.get(transaction.getCreditAccount());
            if (creditAccount != null) {
                creditAccount.creditCount(creditAccount.getCreditCount() + 1);
                creditAccount.balance(creditAccount.getBalance().add(transaction.getAmount()));
            } else {
                creditAccount = createAccount(transaction.getCreditAccount(), 0, 1, transaction.getAmount());
                accountIdToAccount.put(transaction.getCreditAccount(), creditAccount);
            }
        }

        return accountIdToAccount.values().stream()
                .sorted(Comparator.comparing(Account::getAccount))
                .toList();

    }

    private static Account createAccount(String accountId, int debitCount, int creditCount, BigDecimal balance) {
        Account debitAccount = new Account();
        debitAccount.account(accountId);
        debitAccount.setDebitCount(debitCount);
        debitAccount.setCreditCount(creditCount);
        debitAccount.setBalance(balance);
        return debitAccount;
    }
}
