package it.filippocavallari.assignment2.api;

import io.vertx.core.json.JsonObject;

import java.util.List;

public interface InterfaceReport {

    String getFullClassName();

    String getSrcFullFileName();

    List<MethodInfo> getMethodsInfo();

    JsonObject toJson();

}
