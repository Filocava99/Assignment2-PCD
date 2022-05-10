package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import it.filippocavallari.assignment2.api.implementation.InterfaceReportImpl;

public class InterfaceVisitor extends VoidVisitorAdapter<InterfaceReportImpl> implements BaseVisitor{

    @Override
    public void visit(LocalClassDeclarationStmt n, InterfaceReportImpl arg) {
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, InterfaceReportImpl arg) {
        arg.setName(n.getNameAsString());
        n.getFields().forEach(it -> visit(it, arg));
        n.getMethods().forEach(it -> visit(it, arg));
    }

    @Override
    public void visit(MethodDeclaration n, InterfaceReportImpl arg) {
        super.visit(n, arg);
        arg.getMethodsInfo().add(parseMethod(n, arg));
    }

}
