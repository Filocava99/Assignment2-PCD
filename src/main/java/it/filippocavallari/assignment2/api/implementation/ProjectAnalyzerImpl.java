package it.filippocavallari.assignment2.api.implementation;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import it.filippocavallari.assignment2.api.*;
import it.filippocavallari.assignment2.verticles.AnalyzerVerticle;
import it.filippocavallari.assignment2.visitor.ClassVisitor;
import it.filippocavallari.assignment2.visitor.InterfaceVisitor;
import it.filippocavallari.assignment2.visitor.PackageVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProjectAnalyzerImpl implements ProjectAnalyzer {
    private final Vertx vertx;

    public ProjectAnalyzerImpl(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public Future<ClassReport> getClassReport(String srcClassPath) {
        return parseFile(srcClassPath, new ClassVisitor(), new ClassReportImpl()).map(it -> (ClassReport) it);
    }

    @Override
    public Future<PackageReport> getPackageReport(String srcPackagePath) {
        return vertx.fileSystem().readDir(srcPackagePath).compose(mapper -> {
            List<Future> futures = new ArrayList<>();
            var report = new PackageReportImpl();
            mapper.forEach(file -> {
                if (!Files.isDirectory(Path.of(file))) {
                    futures.add(parseFile(file, new PackageVisitor(), report));
                }
            });
            return CompositeFuture.all(futures).map(report);
        });
    }

    @Override
    public Future<ProjectReport> getProjectReport(String srcProjectFolderPath) {
        return vertx.executeBlocking(future -> {
            List<Future<InterfaceReport>> futures = parseProject(srcProjectFolderPath);
            var report = new ProjectReportImpl();
            CompositeFuture.all(new ArrayList<>(futures)).onComplete(handler -> {
                System.out.println(handler.succeeded());
                handler.result().list().stream().map(result -> (InterfaceReport) result).forEach(classReport -> {
                    System.out.println(classReport.getFullClassName());
                    if(classReport instanceof ClassReport) {
                        report.getAllClasses().add((ClassReport)classReport);
                        if (classReport.getMethodsInfo().stream().anyMatch(methodInfo -> methodInfo.getName().equalsIgnoreCase("main"))) {
                            report.setMainClass((ClassReport)classReport);
                        }
                    }else{
                        report.getAllInterfaces().add(classReport);
                    }
                });
                future.complete(report);
            });
        });
    }

    public void analyzeProject(String srcProjectFolderName, String topic) {
        EventBus eventBus = vertx.eventBus();
        vertx.deployVerticle(new AnalyzerVerticle(topic));
        eventBus.publish("start", srcProjectFolderName);
    }

    private <T> Future<T> parseFile(String filePath, VoidVisitorAdapter<T> visitor, T arg) {
        return vertx.fileSystem().readFile(filePath).compose(result -> {
            String content = new String(result.getBytes());
            return vertx.executeBlocking(future -> {
                System.out.println(filePath + ": ");
                visitor.visit(StaticJavaParser.parse(content), arg);
                System.out.println("completed");
                future.complete(arg);
            });
        });
    }

    private List<Future<InterfaceReport>> parseProject(String subDir) {
        List<Future<InterfaceReport>> interfaceReports = new LinkedList<>();
        Arrays.stream(new File(subDir).listFiles()).forEach(file -> {
            if (file.isDirectory()) {
                interfaceReports.addAll(parseProject(file.getPath()));
            } else {
                boolean flag = false;
                try {
                    flag = isClass(StaticJavaParser.parse(file));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(flag){
                    interfaceReports.add(parseFile(file.getPath(), new ClassVisitor(), new ClassReportImpl()).map(it -> it));
                }else{
                    interfaceReports.add(parseFile(file.getPath(), new InterfaceVisitor(), new InterfaceReportImpl()).map(it -> it));
                }
            }
        });
        return interfaceReports;
    }

    private boolean isClass(CompilationUnit cu){
        var classFilterVisitor = new VoidVisitorAdapter<AtomicBoolean>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, AtomicBoolean arg) {
                arg.set(!n.isInterface());
            }
        };

        var result = new AtomicBoolean();
        classFilterVisitor.visit(cu, result);
        return result.get();
    }

}
