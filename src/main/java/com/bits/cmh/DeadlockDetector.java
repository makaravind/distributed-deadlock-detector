package com.bits.cmh;

import com.bits.Util;
import com.bits.models.InitiatorInfo;
import java.util.Set;

/**
 * CHM based deadlock detection algorithm
 * */
public class DeadlockDetector {

    public boolean detect(Set<Process> graph, Process initiator) {
        DeadlockController controller = new DeadlockController(graph);
        System.out.println("Prepping process for running the deadlock detection algo");
        Set<Process> N = Util.uniqProcesses(graph);
        for (Process process : N) {
            process.prep(N.size());
        }
        System.out.println("Initiating diffusion computation with P" + initiator.getID());
        initiator.sendQuery(new InitiatorInfo(initiator, controller));
        return controller.isDeadlockDetected();
    }
}
