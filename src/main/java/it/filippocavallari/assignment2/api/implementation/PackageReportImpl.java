package it.filippocavallari.assignment2.api.implementation;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.PackageReport;

import java.util.LinkedList;
import java.util.List;

public class PackageReportImpl implements PackageReport {

    private String name;
    private final List<ClassReport> classReports = new LinkedList<>();

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFullPackageName() {
        return name;
    }

    @Override
    public List<ClassReport> getClassReports() {
        return classReports;
    }

    @Override
    public JsonObject toJson() {
        JsonArray classesNames = new JsonArray();
        classReports.stream().map(ClassReport::getFullClassName).forEach(classesNames::add);
        return new JsonObject().put("type", "package").put("name", name).put("classes", classesNames);
    }
}
