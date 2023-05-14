package io.github.kozub.inggreencode.atmservice;

import io.github.kozub.inggreencode.generated.api.AtmServiceApi;
import io.github.kozub.inggreencode.generated.model.ATM;
import io.github.kozub.inggreencode.generated.model.Task;
import jakarta.inject.Inject;

import java.util.List;

class AtmServiceController implements AtmServiceApi {

    AtmServiceCalculator calculator;

    @Inject
    public AtmServiceController(AtmServiceCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public List<ATM> calculate(List<Task> task) {
        return calculator.calculate(task);
    }
}
