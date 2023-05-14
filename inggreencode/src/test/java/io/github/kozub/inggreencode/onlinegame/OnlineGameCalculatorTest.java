package io.github.kozub.inggreencode.onlinegame;

import io.github.kozub.inggreencode.generated.model.Clan;
import io.github.kozub.inggreencode.generated.model.Players;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class OnlineGameCalculatorTest {

    private final OnlineGameCalculator calculator = new OnlineGameCalculator();

    @Test
    void shouldProcessGroupsWithSingleItem() {
        Clan firstClan = clan(8, 1);
        Clan secondClan = clan(8, 3);
        List<Clan> clans = List.of(firstClan, secondClan);
        Players players = players(clans, 10);

        List<List<Clan>> result = calculator.calculate(players);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals(List.of(firstClan), result.get(0));

        assertEquals(1, result.get(1).size());
        assertEquals(List.of(secondClan), result.get(1));
    }
    @Test
    void shouldSplitIntoTwoGroupsWithProvidedMaxNumOfPoints() {
        Clan firstClan = clan(8, 1);
        Clan secondClan = clan(6, 3);
        Clan thirdClan = clan(3, 2);
        List<Clan> clans = List.of(firstClan, secondClan, thirdClan);
        Players players = players(clans, 10);

        List<List<Clan>> result = calculator.calculate(players);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals(List.of(firstClan), result.get(0));

        assertEquals(2, result.get(1).size());
        assertEquals(List.of(secondClan, thirdClan), result.get(1));
    }

    @Test
    void shouldSortClansInTheGroupByPoints() {
        Clan firstClan = clan(4, 1);
        Clan secondClan = clan(2, 3);
        Clan thirdClan = clan(3, 2);
        List<Clan> clans = List.of(firstClan, secondClan, thirdClan);
        Players players = players(clans, 10);

        List<List<Clan>> result = calculator.calculate(players);

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).size());
        assertEquals(List.of(secondClan, thirdClan, firstClan), result.get(0));
    }

    @Test
    void shouldSortClansInTheGroupBySizeWhenEqualNumberOfPoints() {
        Clan firstClan = clan(1, 1);
        Clan secondClan = clan(3, 1);
        Clan thirdClan = clan(2, 1);
        List<Clan> clans = List.of(firstClan, secondClan, thirdClan);
        Players players = players(clans, 10);

        List<List<Clan>> result = calculator.calculate(players);

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).size());
        assertEquals(List.of(firstClan, thirdClan, secondClan), result.get(0));
    }

    @Test
    void shouldCreateAsBigGroupsAsPossible() {
        Clan firstClan = clan(2, 15);
        Clan secondClan = clan(5, 40);
        Clan thirdClan = clan(3, 45);
        Clan forthClan = clan(1, 12);
        Clan fifthClan = clan(4, 60);
        List<Clan> clans = List.of(firstClan, secondClan, thirdClan,
                forthClan, fifthClan);
        Players players = players(clans, 6);

        List<List<Clan>> result = calculator.calculate(players);

        assertEquals(3, result.size());
        assertEquals(3, result.get(0).size());
        assertEquals(List.of(thirdClan, firstClan, forthClan), result.get(0));

        assertEquals(1, result.get(1).size());
        assertEquals(List.of(fifthClan), result.get(1));

        assertEquals(1, result.get(2).size());
        assertEquals(List.of(secondClan), result.get(2));

    }

    private static Players players(List<Clan> clans, Integer groupCount) {
        Players players = new Players();
        players.setGroupCount(groupCount);
        players.setClans(clans);
        return players;
    }

    private static Clan clan(Integer numOfPlayers, Integer points) {
        Clan clan = new Clan();
        clan.setNumberOfPlayers(numOfPlayers);
        clan.setPoints(points);
        return clan;
    }
}