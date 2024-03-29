package com.sgundersen.durak.control;

import com.sgundersen.durak.core.net.match.MatchClientState;
import com.sgundersen.durak.ui.match.MatchFragment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StateController {

    @Getter
    private final MatchClient matchClient;

    private long nextUpdateMs = 0;

    @Getter
    private boolean waiting = false;

    public boolean isFinished() {
        return getMatchClient().isFinished();
    }

    protected boolean isReady() {
        return matchClient.isUpdated() && System.currentTimeMillis() > nextUpdateMs && !waiting;
    }

    public void onWaitingForState() {
        waiting = true;
    }

    public void onStateReceived() {
        waiting = false;
        nextUpdateMs = System.currentTimeMillis() + 2000;
    }

    public void onNextState(MatchClientState state) {
        if (state != null) {
            matchClient.setNextState(state);
        }
    }

    public void update() {
        matchClient.update();
    }

    public void onTap(MatchFragment matchFragment) {
        if (isFinished()) {
            matchFragment.onMatchFinished();
        }
    }

}
