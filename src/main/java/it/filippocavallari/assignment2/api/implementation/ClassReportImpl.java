package it.filippocavallari.assignment2.api.implementation;

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
}
