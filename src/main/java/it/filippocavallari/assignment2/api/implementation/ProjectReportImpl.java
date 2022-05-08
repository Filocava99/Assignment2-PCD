package it.filippocavallari.assignment2.api.implementation;

import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.InterfaceReport;
import it.filippocavallari.assignment2.api.ProjectReport;

import java.util.LinkedList;
import java.util.List;

public class ProjectReportImpl implements ProjectReport {

    private ClassReport mainClass;
    private List<ClassReport> classReports = new LinkedList<>();
    private List<InterfaceReport> interfaceReports = new LinkedList<>();

    public void setMainClass(ClassReport mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public ClassReport getMainClass() {
        return mainClass;
    }

    @Override
    public List<ClassReport> getAllClasses() {
        return classReports;
    }

    @Override
    public ClassReport getClassReport(String fullClassName) {
        return classReports.stream().filter(classReport -> classReport.getFullClassName().equals(fullClassName)).findFirst().get();
    }

    public List<InterfaceReport> getAllInterfaces() {
        return interfaceReports;
    }
}
