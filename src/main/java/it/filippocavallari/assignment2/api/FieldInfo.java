package it.filippocavallari.assignment2.api;

import io.vertx.core.json.JsonObject;

public interface FieldInfo {

	String getName();

	String getFieldTypeFullName();

	ClassReport getParent();

	JsonObject toJson();
}
