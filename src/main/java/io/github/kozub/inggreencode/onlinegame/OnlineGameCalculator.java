package io.github.kozub.inggreencode.onlinegame;



import io.github.kozub.inggreencode.generated.model.Clan;
import io.github.kozub.inggreencode.generated.model.Players;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

import static java.lang.Integer.compare;

@ApplicationScoped
public class OnlineGameCalculator {

    private static final Comparator<Clan> SORT_BY_POINTS_AND_NUMBER_OF_PLAYERS = (c1, c2) -> {
        int pointsCompare = compare(c2.getPoints(), c1.getPoints());
        return pointsCompare != 0 ? pointsCompare : compare(c1.getNumberOfPlayers(), c2.getNumberOfPlayers());
    };

    public List<List<Clan>> calculate(Players players) {
        return splitGroups(players.getGroupCount(), players.getClans());
    }

    private List<List<Clan>> splitGroups(Integer groupCount, List<Clan> clans) {
        List<List<Clan>> result = new LinkedList<>();
        List<Clan> currentGroup = new ArrayList<>();
        List<Clan> skippedClans = new ArrayList<>();
        int currentGroupCount = 0;

        ListIterator<Clan> iterator = clans.listIterator();

        while (iterator.hasNext()) {
            Clan clan = iterator.next();
            if (clanFitsToCurrentGroup(clan, groupCount, currentGroupCount)) {
                currentGroup.add(clan);
                currentGroupCount += clan.getNumberOfPlayers();
            } else {
                if (iterator.hasNext()) {
                    Clan nextClan = iterator.next();
                    if (clanFitsToCurrentGroup(nextClan, groupCount, currentGroupCount)) {
                        skippedClans.add(clan);
                        currentGroup.add(nextClan);
                        currentGroupCount += nextClan.getNumberOfPlayers();
                    } else {
                        iterator.previous();
                        result.add(sorted(currentGroup));
                        currentGroup = newListWith(clan);
                        currentGroupCount = clan.getNumberOfPlayers();
                    }

                } else {
                    result.add(sorted(currentGroup));
                    currentGroup = newListWith(clan);
                    currentGroupCount = clan.getNumberOfPlayers();
                }
            }

        }
        result.add(sorted(currentGroup));

        if (!skippedClans.isEmpty()) {
            result.addAll(splitGroups(groupCount, skippedClans));
        }

        return result;
    }

    private static boolean clanFitsToCurrentGroup( Clan clan, Integer groupCount, int currentGroupCount) {
        return (clan.getNumberOfPlayers() + currentGroupCount) <= groupCount;
    }

    private static List<Clan> newListWith(Clan clan) {
        List<Clan> list = new ArrayList<>();
        list.add(clan);
        return list;
    }

    private List<Clan> sorted(List<Clan> currentGroup) {
        currentGroup.sort(SORT_BY_POINTS_AND_NUMBER_OF_PLAYERS);
        return currentGroup;
    }
}
