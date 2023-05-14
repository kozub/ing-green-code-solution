package io.github.kozub.inggreencode.onlinegame;

import io.github.kozub.inggreencode.generated.api.OnlineGameApi;
import io.github.kozub.inggreencode.generated.model.Players;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

class OnlineGameController implements OnlineGameApi {

    OnlineGameCalculator calculator;

    @Inject
    public OnlineGameController(OnlineGameCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Response calculate(Players players) {
        if (players == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(calculator.calculate(players)).build();
    }
}
