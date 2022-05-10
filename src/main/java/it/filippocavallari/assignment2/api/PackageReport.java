package it.filippocavallari.assignment2.api;

import io.vertx.core.json.JsonObject;

import java.util.List;

public interface PackageReport {

	String getFullPackageName();

	List<ClassReport> getClassReports();

	JsonObject toJson();

}
