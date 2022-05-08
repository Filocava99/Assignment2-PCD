package it.filippocavallari.assignment2.api;

import java.util.List;

public interface InterfaceReport {

    String getFullClassName();

    String getSrcFullFileName();

    List<MethodInfo> getMethodsInfo();

}
