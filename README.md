# Connectivity Tool

**How to run the tool ?**
1. you'll need java jre installed on the host machine
2. create configuration file, you can see examples for configuration in _src/main/resources_ or in the example files under the root folder
3. change dir to ConnectivityTool
4. in bash/cmd execute:  _java -jar ./build/libs/ConnectivityTool-1.0-SNAPSHOT.jar {pathToFile}_
5. you can view the output in stdout and the same output in the log file  - ./logs/connectivityTool.log


**how to build ?**

you'll need java sdk 20 and gradle
the code was developed with Intellij community edition, so it's best to view it with it.
or you can run ./gradlew jar to create an executable standalone jar 


**Compromises i have made to get the code ready on time:**
1. latency store- i used a simple file to persist the latency between executions, a better solution should provide 
    better performance and would not require to load the entire store or to save it after each update.
2. latency store- a better thread safe solution is needed to provide faster access to the shared resource, i.e the latency store.
3. performance optimization - async logging instead of synchronized
4. performance optimization - http client thread pool control



