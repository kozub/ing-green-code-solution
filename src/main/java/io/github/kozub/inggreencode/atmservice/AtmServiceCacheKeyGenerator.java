package io.github.kozub.inggreencode.atmservice;

import io.github.kozub.inggreencode.generated.model.Clan;
import io.github.kozub.inggreencode.generated.model.Players;
import io.github.kozub.inggreencode.generated.model.Task;
import io.quarkus.cache.CacheKeyGenerator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class AtmServiceCacheKeyGenerator implements CacheKeyGenerator {
    @Override
    public Object generate(Method method, Object... methodParams) {
        List<Task> tasks= (List<Task>) methodParams[0];

        int hashCode = 0;
        for (Task task : tasks) {
            hashCode += Objects.hash(task.getAtmId(), task.getRegion(), task.getRequestType());
        }

        return hashCode;
    }
}
