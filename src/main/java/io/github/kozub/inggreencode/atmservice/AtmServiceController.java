package io.github.kozub.inggreencode.atmservice;

import io.github.kozub.inggreencode.generated.api.AtmServiceApi;
import io.github.kozub.inggreencode.generated.model.Task;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

class AtmServiceController implements AtmServiceApi {

    AtmServiceCalculator calculator;

    @Inject
    public AtmServiceController(AtmServiceCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Response calculate(List<Task> task) {
        if (task == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(calculator.calculate(task)).build();
    }
}
