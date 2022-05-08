package it.filippocavallari.assignment2.controller;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.ProjectAnalyzer;
import it.filippocavallari.assignment2.api.implementation.ProjectAnalyzerImpl;
import it.filippocavallari.assignment2.view.GUI;

import javax.swing.*;

public class MainController {

    public static void main(String[] args){
        GUI gui = new GUI();
        Vertx vertx = Vertx.vertx();
        ProjectAnalyzer projectAnalizer = new ProjectAnalyzerImpl(vertx);
        String address = "proj.analizer";
        projectAnalizer.analyzeProject("src/", address);

        gui.getNextButton().addActionListener((event) -> {
            vertx.eventBus().publish(address, new JsonObject().put("message", "next"));
        });

        vertx.eventBus().consumer(address, handler -> {
            JsonObject response = (JsonObject)handler.body();
            if(response.getString("message").equalsIgnoreCase("visit")){
                SwingUtilities.invokeLater(()-> {
                    gui.getTextArea().append("\n" + response.toString());
                });
            }
        });
    }

}
