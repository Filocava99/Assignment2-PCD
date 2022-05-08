package it.filippocavallari.assignment2.api.implementation;

import it.filippocavallari.assignment2.api.FieldInfo;
import it.filippocavallari.assignment2.api.InterfaceReport;
import it.filippocavallari.assignment2.api.MethodInfo;

import java.util.LinkedList;
import java.util.List;

public class InterfaceReportImpl implements InterfaceReport {
    private String name = "";
    private List<MethodInfo> methods = new LinkedList<>();

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

}
