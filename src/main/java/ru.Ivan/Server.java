package ru.Ivan;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.japi.Pair;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.regex.Pattern;

public class Server {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String TEST_URL = "testUrl";
    private static final String COUNT = "count";
    private static final int MAP_ASYNC = 2;

    public static void main(String[] args) throws IOException {
        System.out.println("Start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = createFlow(http, system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOST, PORT),
                materializer
        );
        System.out.println("Server online at http://" + HOST + ":" + PORT + "/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate()); //and shutdown when done
    }

    private static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(Http http, ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map((req) -> {
                    Query q = req.getUri().query();
                    String url = q.get(TEST_URL).get();
                    int count = Integer.parseInt(q.get(COUNT).get());
                    return new Pair<String, Integer>(url, count);
                })
                .mapAsync(MAP_ASYNC, req -> {
                    CompletionStage<Object> stage = Pattern.ask()
                })
    }
}
