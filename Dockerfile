FROM openjdk:17

COPY target/ExpenseManager.jar ExpenseManager.jar

ENTRYPOINT ["java","-jar","/ExpenseManager.jar"]