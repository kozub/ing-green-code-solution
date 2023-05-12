package io.github.kozub.inggreencode.onlinegame;



import io.github.kozub.inggreencode.generated.model.Clan;
import io.github.kozub.inggreencode.generated.model.Players;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.compare;

@ApplicationScoped
public class OnlineGameCalculator {
    public List<List<Clan>> calculate(Players players) {
        return splitGroups(players.getGroupCount(), players.getClans());
    }

    private List<List<Clan>> splitGroups(Integer groupCount, List<Clan> clans) {
        List<List<Clan>> result = new ArrayList<>();
        List<Clan> currentGroup = new ArrayList<>();
        List<Clan> skippedClans = new ArrayList<>();
        int currentGroupCount = 0;

        for (int i = 0; i < clans.size(); i++) {
            Clan clan = clans.get(i);
            if ((currentGroupCount + clan.getNumberOfPlayers()) <= groupCount) {
                currentGroup.add(clan);
                currentGroupCount += clan.getNumberOfPlayers();
            } else {
                if (clans.size() > i+1 && clans.get(i+1).getNumberOfPlayers() + currentGroupCount <= groupCount) {
                    skippedClans.add(clan);

                    clan  = clans.get(++i);
                    currentGroup.add(clan);
                    currentGroupCount += clan.getNumberOfPlayers();
                } else {
                    result.add(sortedCopy(currentGroup));
                    currentGroup = new ArrayList<>();
                    currentGroup.add(clan);
                    currentGroupCount = clan.getNumberOfPlayers();
                }
            }
        }
        result.add(sortedCopy(currentGroup));

        if (!skippedClans.isEmpty()) {
            result.addAll(splitGroups(groupCount, skippedClans));
        }

        return result;
    }

    private List<Clan> sortedCopy(List<Clan> currentGroup) {
        return currentGroup.stream()
                .sorted((c1,c2) -> {
                    int pointsCompare = compare(c2.getPoints(), c1.getPoints());
                    return pointsCompare != 0 ? pointsCompare : compare(c1.getNumberOfPlayers(), c2.getNumberOfPlayers());
                })
                .toList();
    }
}
