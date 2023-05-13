package io.github.kozub.inggreencode.transactions;

import io.github.kozub.inggreencode.generated.model.Transaction;
import io.quarkus.cache.CacheKeyGenerator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class TransactionCacheKeyGenerator implements CacheKeyGenerator {
    @Override
    public Object generate(Method method, Object... methodParams) {
        List<Transaction> transactions = (List<Transaction>) methodParams[0];

        int hashCode = 0;
        for (Transaction transaction : transactions) {
            hashCode += Objects.hash(transaction.getAmount(), transaction.getDebitAccount(), transaction.getCreditAccount());
        }
        return hashCode;
    }
}
