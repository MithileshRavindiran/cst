# Customer Statement Processing #



* Task

  Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated. based on below conditions

     * all transaction references should be unique
     * end balance needs to be validated



**1. Clone the repository**

```bash

```

**2. Run the app using maven**

```bash
cd customerstatementprocessor
mvn spring-boot:run
```

The application can be accessed at `http://localhost:8080`.



**3. API Documentation**


The application documentation can also be accessed at `http://localhost:8080/swagger-ui.html`.Also tested from swagger end point too

  The Api returns all  transactions instead of the failure records since the API is designed in a such way that it can
  be reusable in future too in  case if we need to fetch the successful records too.

  This  is achieved by the new field's on API

```
     isInvalidRecord - boolean  field set to true if  the record is invalid base on  the current validation logic
     reason - string  holds the reason why  the transaction deemed  to be invalid
```



**4. Project Details**

  * Separation of Concerns
  * Facade pattern, Factory Pattern - Modules are well separated and independent for further Scaling up.
  * Usage of Lombok -Builder pattern which gives a clear picture of separation and implementation.
  * Exception Handling : All the possible constraints are taking into account.