package io.github.kozub.inggreencode.onlinegame;

import io.github.kozub.inggreencode.generated.model.Clan;
import io.github.kozub.inggreencode.generated.model.Players;
import io.quarkus.cache.CacheKeyGenerator;

import java.lang.reflect.Method;
import java.util.Objects;

public class OnlineGameCacheKeyGenerator implements CacheKeyGenerator {
    @Override
    public Object generate(Method method, Object... methodParams) {
        Players players = (Players) methodParams[0];

        int hashCode = players.getGroupCount();
        for (Clan clan : players.getClans()) {
            hashCode += Objects.hash(clan.getNumberOfPlayers(), clan.getPoints());
        }
        return hashCode;
    }
}
