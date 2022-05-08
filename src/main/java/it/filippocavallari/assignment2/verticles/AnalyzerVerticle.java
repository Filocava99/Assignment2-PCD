package it.filippocavallari.assignment2.verticles;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.visitor.AdvancedVisitor;

import java.util.ArrayDeque;
import java.util.Deque;

public class AnalyzerVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final String address;
    private final AdvancedVisitor visitor = new AdvancedVisitor();

    public AnalyzerVerticle(String address, Vertx vertx) {
        this.address = address;
        this.eventBus = vertx.eventBus();
    }

    private Deque<Node> deque = new ArrayDeque<>();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        eventBus.consumer(address, handler -> {
            JsonObject jsonObject = (JsonObject) handler.body();
            switch (jsonObject.getString("message")){
                case "start": {
                    System.out.println("START");
                    vertx.fileSystem().readFile(jsonObject.getString("path")).compose(mapper -> getVertx().executeBlocking(future -> {
                        CompilationUnit compilationUnit = StaticJavaParser.parse(new String(mapper.getBytes()));
                        addElementToBeParsed(compilationUnit);
                        eventBus.send(address, new JsonObject().put("message", "started"));
                    }));
                    break;
                }
                case "next": {
                    System.out.println("NEXT");
                    System.out.println(deque.size());
                    if(!deque.isEmpty()){
                        vertx.executeBlocking(future -> {
                            System.out.println("Inside next future");
                            JsonObject result = new JsonObject();
                            visitor.visit(deque.pop(), result);
                            result.put("message", "visit");
                            future.complete(result);
                        }).onComplete(finalHandler -> {
                            JsonObject result =  (JsonObject) finalHandler.result();
                            eventBus.send(address, result);
                        });
                    }
                }
            }
        });
        super.start(startPromise);
        startPromise.complete();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void addElementToBeParsed(Node node){
        node.getChildNodes().forEach(it -> {
            if(it instanceof PackageDeclaration || it instanceof ClassOrInterfaceDeclaration){
                deque.add(it);
                addElementToBeParsed(it);
            }
        });
    }
}
