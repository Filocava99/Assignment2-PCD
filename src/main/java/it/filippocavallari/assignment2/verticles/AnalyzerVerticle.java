package it.filippocavallari.assignment2.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.ProjectAnalyzer;
import it.filippocavallari.assignment2.visitor.AdvancedVisitor;

import java.io.File;
import java.util.Arrays;


public class AnalyzerVerticle extends AbstractVerticle {
    private final Vertx vertx;
    private final EventBus eventBus;
    private final String address;
    private final AdvancedVisitor visitor = new AdvancedVisitor();
    private final  ProjectAnalyzer projectAnalyzer;

    public AnalyzerVerticle(String address, Vertx vertx, ProjectAnalyzer projectAnalyzer) {
        this.address = address;
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
        this.projectAnalyzer = projectAnalyzer;
    }
    private Future future;

    @Override
    public void start() {
        eventBus.consumer(address, handler -> {
            JsonObject jsonObject = (JsonObject) handler.body();
            switch (jsonObject.getString("message")){
                case "start": {
                    System.out.println("START");
                    future = vertx.executeBlocking(future -> {
                        parsePath(jsonObject.getString("path"));
                    });
                    break;
                }
                case "stop": {
                    System.out.println("STOP");
                }
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        vertx.deploymentIDs().forEach(vertx::undeploy);
    }

    private void parsePath(String path){
        File file = new File(path);
        if(file.isFile()){
            projectAnalyzer.getClassReport(path).onComplete(handler -> {
               eventBus.publish(address, handler.result().toJson().put("message", "visit"));
            });
        }else{
            projectAnalyzer.getPackageReport(path).onComplete(handler -> {
                eventBus.publish(address, handler.result().toJson().put("message", "visit"));
            });
            Arrays.stream(file.listFiles()).map(File::getPath).forEach(this::parsePath);
        }
    }
}
