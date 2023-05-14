package io.github.kozub.inggreencode.atmservice;

import io.github.kozub.inggreencode.generated.model.ATM;
import io.github.kozub.inggreencode.generated.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtmServiceCalculatorTest {

    private final AtmServiceCalculator calculator = new AtmServiceCalculator();

    @Test
    void shouldRemoveATMDuplicates() {

        List<Task> tasks = List.of(
                new Task().atmId(1).region(10).requestType(Task.RequestTypeEnum.PRIORITY),
                new Task().atmId(1).region(9).requestType(Task.RequestTypeEnum.PRIORITY),
                new Task().atmId(1).region(10).requestType(Task.RequestTypeEnum.STANDARD)
        );
        List<ATM> result = calculator.calculate(tasks);

        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getAtmId());
        assertEquals(9, result.get(0).getRegion());

        assertEquals(1, result.get(1).getAtmId());
        assertEquals(10, result.get(1).getRegion());
    }
}