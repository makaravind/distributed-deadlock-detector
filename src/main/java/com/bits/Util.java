package com.bits;

import com.bits.cmh.Process;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {
    public static Set<Process> uniqProcesses(Set<Process> graph) {
        return graph.stream().flatMap(p -> p.getDependents().stream()).collect(Collectors.toSet());
    }


    public static boolean isPresent(List<Integer> a, int v) {
        return a.stream().anyMatch(p -> p == v);
    }

}
