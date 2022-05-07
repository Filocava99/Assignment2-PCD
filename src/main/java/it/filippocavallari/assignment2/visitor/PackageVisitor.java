package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import it.filippocavallari.assignment2.api.implementation.ClassReportImpl;
import it.filippocavallari.assignment2.api.implementation.PackageReportImpl;

public class PackageVisitor extends VoidVisitorAdapter<PackageReportImpl> {

    @Override
    public void visit(ClassOrInterfaceDeclaration n, PackageReportImpl arg) {
        var classReport = new ClassReportImpl();
        new ClassVisitor().visit(n, classReport);
        arg.getClassReports().add(classReport);
    }

    @Override
    public void visit(PackageDeclaration n, PackageReportImpl arg) {
        final String packageName = n.getName().asString();
        arg.setName(packageName);
        super.visit(n, arg);
    }
}
