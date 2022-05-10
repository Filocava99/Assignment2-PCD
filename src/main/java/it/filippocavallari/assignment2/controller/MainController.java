package it.filippocavallari.assignment2.controller;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.ProjectAnalyzer;
import it.filippocavallari.assignment2.api.implementation.ProjectAnalyzerImpl;
import it.filippocavallari.assignment2.verticles.AnalyzerVerticle;
import it.filippocavallari.assignment2.view.GUI;

import javax.swing.*;

public class MainController {

    private static Vertx vertx;
    private static ProjectAnalyzer projectAnalizer;
    private static String address;

    public static void main(String[] args){
        vertx = Vertx.vertx();
        address = "proj.analizer";
        projectAnalizer = new ProjectAnalyzerImpl(vertx);
        vertx.deployVerticle(new AnalyzerVerticle(address, vertx, projectAnalizer));
        setUpView();
    }
    private static void setUpView(){
        GUI gui = new GUI();
        gui.getNextButton().addActionListener((event) -> {
            projectAnalizer.analyzeProject("src/", address);
        });

        vertx.eventBus().consumer(address, handler -> {
            JsonObject response = (JsonObject)handler.body();
            if(response.getString("message").equalsIgnoreCase("visit")){
                SwingUtilities.invokeLater(()-> {
                    gui.getTextArea().append("\n" + response);
                });
            }
        });
    }

}
