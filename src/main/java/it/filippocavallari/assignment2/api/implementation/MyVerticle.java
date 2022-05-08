package it.filippocavallari.assignment2.api.implementation;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.visitor.AdvancedVisitor;
import it.filippocavallari.assignment2.visitor.ClassVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class MyVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final String address;
    private final AdvancedVisitor visitor = new AdvancedVisitor();

    public MyVerticle(String address) {
        this.address = address;
        this.eventBus = vertx.eventBus();
    }

    private Deque<Node> deque = new ArrayDeque<>();

    @Override
    public void start() throws Exception {
        eventBus.consumer(address, handler -> {
            JsonObject jsonObject = (JsonObject) handler;
            switch (jsonObject.getString("message")){
                case "start": {
                    vertx.fileSystem().readFile(jsonObject.getString("path")).compose(mapper -> getVertx().executeBlocking(future -> {
                        CompilationUnit compilationUnit = StaticJavaParser.parse(new String(mapper.getBytes()));
                        addElementToBeParsed(compilationUnit);
                        eventBus.send(address, new JsonObject().put("message", "started"));
                    }));
                    break;
                }
                case "next": {
                    if(!deque.isEmpty()){
                        vertx.executeBlocking(future -> {
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
