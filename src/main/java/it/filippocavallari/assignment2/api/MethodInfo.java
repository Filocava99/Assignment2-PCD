package it.filippocavallari.assignment2.api;

import io.vertx.core.json.JsonObject;

public interface MethodInfo {

	String getName();
	int getSrcBeginLine();
	int getSrcEndLine();
	InterfaceReport getParent();

	JsonObject toJson();
}
