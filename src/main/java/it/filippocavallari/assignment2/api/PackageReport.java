package it.filippocavallari.assignment2.api;

import java.util.List;

public interface PackageReport {

	String getFullPackageName();

	List<ClassReport> getClassReports();

}
