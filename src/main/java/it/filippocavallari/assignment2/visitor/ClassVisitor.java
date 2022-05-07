package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import it.filippocavallari.assignment2.api.implementation.ClassReportImpl;

public class ClassVisitor extends VoidVisitorAdapter<ClassReportImpl> implements BaseVisitor {

    @Override
    public void visit(LocalClassDeclarationStmt n, ClassReportImpl arg) {
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, ClassReportImpl arg) {
        arg.setName(n.getNameAsString());
        System.out.println(n.getNameAsString());
        n.getFields().forEach(it -> visit(it, arg));
        n.getMethods().forEach(it -> visit(it, arg));
    }

    @Override
    public void visit(MethodDeclaration n, ClassReportImpl arg) {
        super.visit(n, arg);
        arg.getMethodsInfo().add(parseMethod(n, arg));
    }

    @Override
    public void visit(FieldDeclaration n, ClassReportImpl arg) {
        super.visit(n, arg);
        arg.getFieldsInfo().addAll(parseField(n, arg));
    }

}
