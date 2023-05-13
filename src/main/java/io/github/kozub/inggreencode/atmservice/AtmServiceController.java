package io.github.kozub.inggreencode.atmservice;

import io.github.kozub.inggreencode.generated.api.AtmServiceApi;
import io.github.kozub.inggreencode.generated.model.ATM;
import io.github.kozub.inggreencode.generated.model.Task;
import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;

import java.util.List;

public class AtmServiceController implements AtmServiceApi {

    AtmServiceCalculator calculator;

    @Inject
    public AtmServiceController(AtmServiceCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    @CacheResult(cacheName = "atmservice-cache")
    public List<ATM> calculate(List<Task> task) {
        return calculator.calculate(task);
    }
}
