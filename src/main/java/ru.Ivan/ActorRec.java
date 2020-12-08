package ru.Ivan;

import akka.actor.AbstractActor;

import java.util.HashMap;

public class ActorRec extends AbstractActor {
    private final HashMap<String, Integer> storage = new HashMap<>();

    @Override
    public Receive createReceive() {
        return null;
    }

    public Receive
}
