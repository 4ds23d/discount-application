# About

This is a simple application that calculates product discounts based on configurations stored in a database. The application is written in the Kotlin programming language and uses the Spring Boot framework.

## Further improvements

1. Discount configuration can be improved by adding: `validFrom:time, validTo:time`. Including this information in the discount configuration can greatly improve the operational side of this domain, since the configuration can be prepared many days before the actual date.
2. In addition, you can make a simulation by adding a parameter that will tell you on what date the discount should be counted.
3. Adding validation to parameters
4. Adding logs

# Run application

## Run tests

```bash
./gradlew clean test
```

## Run application

To run the application, the database must be available and ready to accept connections.
You can start the database using the following command:
```bash
docker-compose -f ./docker/setup-db.yml start
```

Once the external dependencies are ready, you can run the application using one of the following methods:

### Running with Gradle
```bash
./gradlew bootRun
```

### Running with Java from a JAR File

1. Build the JAR file:
```bash
./gradlew bootRun
```
2. The JAR file will be located in the build/libs directory.


# Testing application

## Register product

```bash
curl --header "Content-Type: application/json" \
--request POST \
--data '{"description":"description","price":"100"}' \
http://localhost:8080/api/v1/products/67fe40ed-abb4-454f-95f6-95ba6168fda5
```

## Get product description

```bash
curl http://localhost:8080/api/v1/products/67fe40ed-abb4-454f-95f6-95ba6168fda5/description
```

## Register percentage discount 

```bash
curl --header "Content-Type: application/json" \
--request POST \
--data '{"@type":"PercentageV1","percentage":{"value":5}}' \
http://localhost:8080/api/v1/discount/67fe40ed-abb4-454f-95f6-95ba6168fda5
```

## Register quantity based discount

```bash
curl --header "Content-Type: application/json" \
--request POST \
--data '{"@type":"QuantityBasedDiscountV1","configuration":[{"from":0,"to":9,"percentage":{"value":0}},{"from":10,"to":19,"percentage":{"value":5}},{"from":20,"to":49,"percentage":{"value":10}},{"from":50,"to":null,"percentage":{"value":15}}]}' \
http://localhost:8080/api/v1/discount/67fe40ed-abb4-454f-95f6-95ba6168fda5
```

## Compute discount for product

```bash
curl http://localhost:8080/api/v1/discount/67fe40ed-abb4-454f-95f6-95ba6168fda5?amount=10
```