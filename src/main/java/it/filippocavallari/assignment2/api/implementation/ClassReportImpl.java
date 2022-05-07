package it.filippocavallari.assignment2.api.implementation;

import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.FieldInfo;
import it.filippocavallari.assignment2.api.MethodInfo;

import java.util.LinkedList;
import java.util.List;

public class ClassReportImpl implements ClassReport {

    private String name = "";
    private List<MethodInfo> methods = new LinkedList<>();
    private List<FieldInfo> fields = new LinkedList<>();

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFullClassName() {
        return name;
    }

    @Override
    public String getSrcFullFileName() {
        return null;
    }

    @Override
    public List<MethodInfo> getMethodsInfo() {
        return methods;
    }

    @Override
    public List<FieldInfo> getFieldsInfo() {
        return fields;
    }
}
