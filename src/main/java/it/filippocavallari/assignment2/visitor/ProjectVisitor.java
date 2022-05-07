package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import it.filippocavallari.assignment2.api.implementation.ClassReportImpl;
import it.filippocavallari.assignment2.api.implementation.ProjectReportImpl;

public class ProjectVisitor extends VoidVisitorAdapter<ProjectReportImpl> implements BaseVisitor {

    @Override
    public void visit(ClassOrInterfaceDeclaration n, ProjectReportImpl arg) {
        super.visit(n, arg);
        ClassReportImpl classReport = new ClassReportImpl();
        new ClassVisitor().visit(n, classReport);
        if(classReport.getMethodsInfo().stream().anyMatch(methodInfo -> methodInfo.getName().equalsIgnoreCase("main"))){
            arg.setMainClass(classReport);
        }
        arg.getAllClasses().add(classReport);
    }
}
