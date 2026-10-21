test:
	mvn clean && mvn compile && mvn exec:java -Dexec.mainClass="backendTest"

chess:
	mvn clean && mvn compile && mvn exec:java -Dexec.mainClass="chess"