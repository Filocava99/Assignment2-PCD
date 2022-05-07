package it.filippocavallari.assignment2;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import it.filippocavallari.assignment2.api.implementation.ProjectAnalyzerImpl;

import java.util.List;

class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
  public void visit(MethodDeclaration md, List<String> collector) {
	  super.visit(md, collector);
	  collector.add(md.getNameAsString());
  }
}

class FullCollector extends VoidVisitorAdapter<Void> {

	public void visit(PackageDeclaration fd, Void collector) {
		super.visit(fd, collector);
		System.out.println(fd);
	}

	public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
		super.visit(cd, collector);
		System.out.println(cd.getNameAsString());
	}
	
	public void visit(FieldDeclaration fd, Void collector) {
		super.visit(fd, collector);
		System.out.println(fd);
	}

	public void visit(MethodDeclaration md, Void collector) {
		super.visit(md, collector);
		System.out.println(md.getName());
	}
}


public class TestJavaParser {

	public static void main(String[] args) throws Exception {;
		var projectAnalizer = new ProjectAnalyzerImpl();
		projectAnalizer.getProjectReport("src/").onComplete(result -> {
			result.result().getAllClasses().forEach(it -> System.out.println(it.getFullClassName()));
			System.out.println("Main class: " + result.result().getMainClass().getFullClassName());
			projectAnalizer.stopVertex();
		});
	}
}
