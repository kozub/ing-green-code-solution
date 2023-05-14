package io.github.kozub.inggreencode.atmservice;

import io.github.kozub.inggreencode.generated.model.ATM;
import io.github.kozub.inggreencode.generated.model.Task;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@ApplicationScoped
class AtmServiceCalculator {

    private static final Comparator<ATM> SORT_BY_REGION = Comparator.comparing(ATM::getRegion);
    private static final Comparator<Task> SORT_BY_PRIORITY = Comparator.comparingInt(AtmServiceCalculator::findPriority);

    public List<ATM> calculate(List<Task> tasks) {
        return tasks.stream()
                .collect(groupingBy(Task::getRegion)).values().stream()
                .flatMap(this::sortByPriorityAndRemoveDuplicates)
                .map(AtmServiceCalculator::toATM)
                .sorted(SORT_BY_REGION)
                .toList();
    }

    private Stream<Task> sortByPriorityAndRemoveDuplicates(List<Task> tasksInRegion) {
        return tasksInRegion.stream()
                .sorted(SORT_BY_PRIORITY)
                .filter(distinctByKey(Task::getAtmId));
    }

    private static ATM toATM(Task task) {
        return new ATM()
                .atmId(task.getAtmId())
                .region(task.getRegion());
    }

    private static int findPriority(Task task) {
        return switch (task.getRequestType()) {
            case FAILURE_RESTART -> 1;
            case PRIORITY -> 2;
            case SIGNAL_LOW -> 3;
            case STANDARD -> 4;
            default -> throw new IllegalStateException("Unsupported type of task: " + task.getRequestType() + ".");
        };
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
