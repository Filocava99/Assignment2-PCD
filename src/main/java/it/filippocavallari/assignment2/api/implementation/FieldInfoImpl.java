package it.filippocavallari.assignment2.api.implementation;

import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.FieldInfo;

public class FieldInfoImpl implements FieldInfo {

    private final String name;
    private final String typeName;
    private final ClassReport parent;

    public FieldInfoImpl(String name, String typeName, ClassReport parent) {
        this.name = name;
        this.typeName = typeName;
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFieldTypeFullName() {
        return typeName;
    }

    @Override
    public ClassReport getParent() {
        return parent;
    }

    @Override
    public JsonObject toJson() {
        return new JsonObject().put("type", "field").put("name", name).put("fieldType", typeName).put("parent", parent.getFullClassName());
    }
}
