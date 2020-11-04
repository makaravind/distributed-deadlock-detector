package com.bits.models;

import com.bits.cmh.IDeadlockDetectorController;
import com.bits.cmh.Process;

public class InitiatorInfo {
    private Process initiator;
    private IDeadlockDetectorController controller;

    public InitiatorInfo(Process initiator, IDeadlockDetectorController controller) {
        this.initiator = initiator;
        this.controller = controller;
    }

    public Process getInitiator() {
        return initiator;
    }

    public void setInitiator(Process initiator) {
        this.initiator = initiator;
    }

    public IDeadlockDetectorController getController() {
        return controller;
    }

    public void setController(IDeadlockDetectorController controller) {
        this.controller = controller;
    }
}
