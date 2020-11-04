package com.bits.cmh;

import java.util.Set;

public class DeadlockController implements IDeadlockDetectorController {

    private final Set<Process> graph;
    private boolean deadlockDetected = false;

    public DeadlockController(Set<Process> graph) {
        this.graph = graph;
    }

    public boolean isDeadlockDetected() {
        return deadlockDetected;
    }

    @Override
    public void onDeadlockDetected(Process atProcess) {
        for (Process process : this.graph) {
            process.stop();
        }
        this.deadlockDetected = true;
    }
}
