package io.github.kozub.inggreencode.onlinegame;

import io.github.kozub.inggreencode.generated.api.OnlineGameApi;
import io.github.kozub.inggreencode.generated.model.Clan;
import io.github.kozub.inggreencode.generated.model.Players;
import jakarta.inject.Inject;

import java.util.List;

class OnlineGameController implements OnlineGameApi {

    OnlineGameCalculator calculator;


    @Inject
    public OnlineGameController(OnlineGameCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public List<List<Clan>> calculate(Players players) {
        return calculator.calculate(players);
    }
}
