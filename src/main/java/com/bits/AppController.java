package com.bits;

import com.bits.Util;
import com.bits.cmh.DeadlockDetector;
import com.bits.cmh.Process;
import com.bits.models.ParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppController {
    private Set<Process> graph;

    public Set<Process> getGraph() {
        return graph;
    }

    public void setGraph(Set<Process> graph) {
        this.graph = graph;
    }

    public HashSet<Process> constructGraph(Path file) throws ParseException {
        try (Stream<String> stream = Files.lines(file)) {
            final Pattern patten = Pattern.compile("(\\d): ([\\d,]+)");
            Map<Integer, Process> uniqAllProcesses = new HashMap<>();
            ArrayList<Process> graphList = stream.reduce(new ArrayList<>(), (accGraph, processConfig) -> {
                Matcher matcher = patten.matcher(processConfig);
                if (matcher.find()) {
                    int pId = Integer.parseInt(matcher.group(1));
                    String[] dependentIds = matcher.group(2).split(",");
                    Process p = uniqAllProcesses.getOrDefault(pId, new Process(pId));
                    uniqAllProcesses.put(p.getID(), p);
                    Set<Process> dependentProcesses = Arrays.stream(dependentIds)
                            .map(Integer::parseInt)
                            .map(id -> {
                                if (uniqAllProcesses.containsKey(id)) {
                                    return uniqAllProcesses.get(id);
                                }
                                Process pq = new Process(id);
                                uniqAllProcesses.put(id, pq);
                                return pq;
                            })
                            .collect(Collectors.toSet());
                    p.setDependents(dependentProcesses);
                    accGraph.add(p);
                    return accGraph;
                } else {
                    System.out.println("Invalid process configuration: skipped");
                }
                return accGraph;
            }, (g, h) -> {
                // Only invoked when run in parallel
                ArrayList<Process> processes = new ArrayList<>();
                processes.addAll(g);
                processes.addAll(h);
                return processes;
            });
            return new HashSet<>(graphList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParseException();
        }
    }

    private int confirmInitiator() {
        Scanner input = new Scanner(System.in);
        List<Integer> processes = Util.uniqProcesses(this.graph).stream().map(Process::getID).collect(Collectors.toList());
        int v;
        do {
            System.out.println("Enter process initiator ID: " + processes);
            v = input.nextInt();
        } while (!Util.isPresent(processes, v));
        return v;
    }

    public boolean isDeadlockPresentOnInputFile(Path file) throws ParseException {
        this.graph = constructGraph(file);
        return isDeadlockPresent();
    }

    private boolean isDeadlockPresent() {
        if (Objects.isNull(this.graph)) {
            throw new IllegalStateException("Graph is not constructed");
        }
        int selectedInitiator = this.confirmInitiator();
        DeadlockDetector d = new DeadlockDetector();
        return d.detect(this.graph, findProcessById(selectedInitiator));
    }

    public Process findProcessById(int id) {
        return this.graph.stream().filter(p -> p.equals(new Process(id)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find the process in graph with ID " + id));
    }
}
