package it.filippocavallari.assignment2.api.implementation;

import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.PackageReport;
import it.filippocavallari.assignment2.api.ProjectElem;

import java.util.LinkedList;
import java.util.List;

public class PackageReportImpl implements PackageReport, ProjectElem {

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
}
