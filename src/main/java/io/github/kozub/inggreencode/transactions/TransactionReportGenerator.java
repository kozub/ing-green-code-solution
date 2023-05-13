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

    private static final Comparator<Account> SORT_BY_ACCOUNT = Comparator.comparing(Account::getAccount);

    public List<Account> generateReport(List<Transaction> transactions) {
        int estimatedInitialCapacity = Math.max((transactions.size()/10) + 1, 16);
        Map<String, Account> accountIdToAccount = new HashMap<>(estimatedInitialCapacity);

        for (Transaction transaction : transactions) {
            Account debitAccount = accountIdToAccount.get(transaction.getDebitAccount());
            if (debitAccount != null) {
                int debitCount = debitAccount.getDebitCount();
                debitAccount.debitCount(++debitCount);
                var newBalance = debitAccount.getBalance().subtract(transaction.getAmount());
                debitAccount.balance(newBalance);

            } else {
                debitAccount = createAccount(transaction.getDebitAccount(), 1, 0, transaction.getAmount().negate());
                accountIdToAccount.put(transaction.getDebitAccount(), debitAccount);
            }

            Account creditAccount = accountIdToAccount.get(transaction.getCreditAccount());
            if (creditAccount != null) {
                int creditCount = creditAccount.getCreditCount();
                creditAccount.creditCount(++creditCount);
                var newBalance = creditAccount.getBalance().add(transaction.getAmount());
                creditAccount.balance(newBalance);

            } else {
                creditAccount = createAccount(transaction.getCreditAccount(), 0, 1, transaction.getAmount());
                accountIdToAccount.put(transaction.getCreditAccount(), creditAccount);
            }
        }

        return sortByAccount(accountIdToAccount);
    }

    private static List<Account> sortByAccount(Map<String, Account> accountIdToAccount) {
        return accountIdToAccount.values()
                .stream().sorted(SORT_BY_ACCOUNT)
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
