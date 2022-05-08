package it.filippocavallari.assignment2.controller;

import io.vertx.core.Vertx;
import it.filippocavallari.assignment2.api.ProjectAnalyzer;
import it.filippocavallari.assignment2.api.implementation.ProjectAnalyzerImpl;
import it.filippocavallari.assignment2.view.GUI;

public class MainController {

    public static void main(String[] args){
        GUI gui = new GUI();
        Vertx vertx = Vertx.vertx();
        ProjectAnalyzer projectAnalizer = new ProjectAnalyzerImpl(vertx);
        projectAnalizer.analyzeProject("src/", "proj.analizer");
    }

}
