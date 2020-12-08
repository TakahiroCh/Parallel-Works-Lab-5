package ru.Ivan;

import akka.actor.ActorSystem;

public class Server {
    public static void main(String[] args) {
        System.out.println("Start!");
        ActorSystem system = ActorSystem.create("routes");
        

    }
}
