package it.filippocavallari.assignment2.api.implementation;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.FieldInfo;

import java.util.LinkedList;
import java.util.List;

public class ClassReportImpl extends InterfaceReportImpl implements ClassReport {
    private List<FieldInfo> fields = new LinkedList<>();

    @Override
    public List<FieldInfo> getFieldsInfo() {
        return fields;
    }

    @Override
    public JsonObject toJson() {
        JsonArray fieldsArray = new JsonArray();
        fields.stream().map(FieldInfo::toJson).forEach(fieldsArray::add);
        return super.toJson().put("fields", fieldsArray);
    }
}
