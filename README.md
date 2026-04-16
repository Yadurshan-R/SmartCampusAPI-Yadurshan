# 5COSC022C.2: Client-Server Architectures (SmartCampusAPI)

<br>

The CSA Report Containing The Answers To The Questions:  
[20220329-w1957961-CSA-Report.pdf](./20220329-w1957961-CSA-Report.pdf)

## Personal Information

<br>

- Name Of The Student: Yadurshan Rajakumar

- Module Code: 5COSC022C.2

- IIT Student Identification: 20220329

- University Of Westminster Number: w1957961

<br>
<hr>

## Overview Of The SmartCampusAPI Implementation

<br>

The 'Client-Server Architectures' coursework required me to design and implement a SmartCampusAPI. The SmartCampusAPI is a RESTful web service developed using JAX-RS and deployed on Apache Tomcat. The API is designed to manage rooms, sensors, and sensor readings within a smart campus environment. The API follows RESTful design principles and provides structured access to resources through well-defined endpoints.

The system supports core functionalities such as creating and retrieving rooms, linking sensors to specific rooms, and managing sensor readings as sub-resources. It also includes advanced features such as filtering using query parameters, proper use of HTTP status codes, global exception handling, and request/response logging. Overall, the API demonstrates a scalable and maintainable client-server architecture, ensuring clear communication between the client and server.

<br>
<hr>

## Overview Of The SmartCampus API Design

<br>

The SmartCampusAPI is designed following RESTful architectural principles, where resources are clearly defined and accessed through standard HTTP methods. The API is structured around three main entities:

<br>

1. <b>Rooms:</b> Rooms represent physically available locations within the campus and serve as the primary resource.

2. <b>Sensors:</b> Sensors are associated with rooms and are linked using unique room identification numbers.

3. <b>Sensor Readings:</b> Sensor readings are implemented as sub resources of sensors, allowing each sensor to record and maintain recorded values.

<br>

The SmartCampusAPI uses standard HTTP methods such as GET, POST, and DELETE to perform operations on the resources. The URLs are listed below:

<br>

- http://localhost:8080/SmartCampusAPI/api/v1/rooms
- http://localhost:8080/SmartCampusAPI/api/v1/sensors
- http://localhost:8080/SmartCampusAPI/api/v1/sensors/{id}/readings

<br>

Filtering is implemented using query parameters, allowing clients to refine results without changing the resource structure, such as retrieving sensors by type.The SmartCampusAPI also incorporates proper HTTP status codes to indicate the outcome of requests such as 201 is used for successful creation, 404 for resources not found, 409 for conflicts, and 422 for semantic errors in valid requests.

Additionally, the SmartCampusAPI design includes global exception handling to ensure consistent error responses, and logging filters to monitor incoming requests and outgoing responses. Overall, the API is designed to be modular, scalable, and easy to maintain, following best practices in client-server architecture.

<br>
<hr>

## Getting Started And Installing And Running The SmartCampusAPI

<br>

The following steps provide a detailed guide to installing and running the SmartCampusAPI.

### The Prerequisites Required Before Installing And Running The SmartCampusAPI:

1. Java Along With The JDK (Java Development Kit) Version 18+
2. Apache Maven
3. Apache Tomcat Server Version 9 (Recommended)
4. Apache NetBeans IDE (Integrated Development Environment)

### The Detailed Guide For Installing And Running The SmartCampusAPI

<br>

1. Clone the remote repository from GitHub to your local machine's environment

    ```sh
    git clone
    ```

<br>

2. Open the cloned respository in Apache NetBeans IDE (Integrated Development Environment)

<br>

3. Ensure That Apache Tomcat Server Is Configured In Apache Netbeans Via Services

<br>

4. Go To The Run Menu In Apache NetBeans IDE And Click On The 'Clean And Build' Option

<br>

5. Go Back To The Run Menu In Apache NetBeans IDE And Click On The 'Run' Option

<br>

6. Apache NetBeans Will Open A Browser Window With The Specified URL (http://localhost:8080/SmartCampusAPI/api/v1)

<br>

### Sample curl Requests Of The SmartCampusAPI Implementation

The following examples demonstrate how the SmartCampusAPI behaves starting from an empty state after restarting the server:

<br>

1. Command 01: Running The SmartCampusAPI For The First Time And Verifying If It Is A Clean Run.

    ```sh
    curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/rooms
    ``` 

    The Response Of Command 01:

    ```sh
    []
    ```

2. Command 02: Creating A Room In The SmartCampusAPI Implementation

    ```sh
    curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/rooms \
    -H "Content-Type: application/json" \
    -d '{"name":"SP 7LA","capacity":70}
    ```

     The Response Of Command 02:

     ```json
        {
          "id": "R1",
          "name": "SP 7LA",
          "capacity": 70,
          "sensorIds": []
        }
    ```

3. Command 03: Retrieving All The Rooms Created In The SmartCampusAPI Implementation

    ```sh
    curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/rooms
    ```

    The Response Of Command 03:

    ```json
        [
          {
             "id": "R1",
             "name": "SP 7LA",
             "capacity": 70,
             "sensorIds": []
          }
        ]
    ```

4. Command 04: Creating A Sensor And Linking It To A Created Room In The SmartCampusAPI Implementation

    ```sh
    curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors \
    -H "Content-Type: application/json" \
    -d '{"type":"CO2","status":"ACTIVE","roomId":"R1"}'
    ```

    The Response Of Command 04:

    ```json
    {
      "id": "S1",
      "type": "CO2",
      "status": "ACTIVE",
      "roomId": "R1",
      "currentValue": 0.0
    }
    ```

5. Command 05: Filtering The Sensors Created And Linked By Type In The SmartCampusAPI Implementation

    ```sh
    curl -X GET "http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=CO2"
    ```

    The Response Of Command 05:

    ```json
    [
      {
         "id": "S1",
         "type": "CO2",
         "status": "ACTIVE",
         "roomId": "R1",
         "currentValue": 0.0
      }
    ]
    ```