package it.filippocavallari.assignment2.api;

public interface MethodInfo {

	String getName();
	int getSrcBeginLine();
	int getSrcEndLine();
	ClassReport getParent();
		
}
