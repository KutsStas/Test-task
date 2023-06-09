# Test-task for Java Developer position

RESTfull web service for internal company management. Teams consist of employees of different levels (
Jun/Mid/Senior/TechLead) and types (Manager, Developer, QA, DevOps, SysAdmin). All of them can work on several projects
at the same time.
Relational database is used for data storage.

## Software requirements

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven](https://maven.apache.org/)
* [PostgreSQL](https://www.postgresql.org/docs/)

## Getting started

Before starting the application, substitute your values in `application.properties` file.
![img_2.png](img_2.png)

## Description

We have two entities:

* The first Employees with the following fields:

![img_1.png](img_1.png)

The second is projects:

![img.png](img.png)

They correspond to these entities
relational tables in the Database. They are connected to each other by means of a connecting table.

## This API allows you to receive data using the following HTTP methods:

> **GET/employees?id=**

_get employee by ID_

**Accepts parameter**: integer id

**Returns**: employee object

> **POST/employees**

_create employee_

**Accepts body:** employee object

**Returns:** integer id

> **PUT/employees**

_change employee data_

**Accepts body:** employee object

**Returns**: employee object

> **DELETE/employees?id=1**

_delete employee_

**Accepts parameter:** integer id

**Returns:** boolean

> **GET/employees/all**

_get list employees_

**Returns:** array employee objects

> **PUT/employees/remove?employeeId= &projectId=**

_remove the employee from the project_

**Accepts parameter:** integer employeeId, integer projectId

**Returns:** boolean

> **PUT//employees/hire?employeeId= &projectName=**

_set project to employee_

**Accepts parameter:** integer employeeId, sting projectName

**Returns:** boolean

> **GET/employees/project?projectName=**

_get list employee by project name_

**Accepts parameter:** integer employeeId, sting projectName

**Returns:** array employee objects

> **GET/employees/department?department=**

_get list employee by department_

**Accepts parameter:** MANAGER, DEVELOPER, QA, DEVOPS, SYS_ADMIN

**Returns:** array employee objects


> **GET/projects?id=**

_get project by ID_

**Accepts parameter:** integer id

**Returns:** project object

> **POST/projects**

_create project_

**Accepts body:** project object

**Returns:** integer id

> **PUT/projects**

_change project data_

**Accepts body:** project object

**Returns:** project object

> **DELETE/projects?id=**

_delete project_

**Accepts parameter:** integer id

**Returns:** boolean

> **GET/projects/all**

_get projects list_

**Returns:** array project objects

> **GET/projects/status?status=**

_get project list by status_

**Accepts parameter:** NOT_STARTED, IN_PROGRESS, COMPLETE, OVERDUE, ON_HOLD

**Returns:** array project objects



