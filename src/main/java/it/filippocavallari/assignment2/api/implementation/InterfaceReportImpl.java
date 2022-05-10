package it.filippocavallari.assignment2.api.implementation;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.FieldInfo;
import it.filippocavallari.assignment2.api.InterfaceReport;
import it.filippocavallari.assignment2.api.MethodInfo;

import java.util.LinkedList;
import java.util.List;

public class InterfaceReportImpl implements InterfaceReport {
    private String name = "";
    private String srcFullFileName = "";
    private List<MethodInfo> methods = new LinkedList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setSrcFullFileName(String srcFullFileName) {
        this.srcFullFileName = srcFullFileName;
    }

    @Override
    public String getFullClassName() {
        return name;
    }

    @Override
    public String getSrcFullFileName() {
        return srcFullFileName;
    }

    @Override
    public List<MethodInfo> getMethodsInfo() {
        return methods;
    }

    @Override
    public JsonObject toJson() {
        JsonArray methodsArray = new JsonArray();
        methods.stream().map(MethodInfo::toJson).forEach(methodsArray::add);
        return new JsonObject().put("type", "interface").put("name", name).put("src", srcFullFileName).put("methods", methodsArray);
    }
}
