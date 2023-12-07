qall:

	javac -classpath ~/apache-tomcat-10.0.26/lib/servlet-api.jar p2/*.java
	java p2/Main
	mv p2/*.class webapps/WEB-INF/classes/p2/