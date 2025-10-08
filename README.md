# homework
QA homework assignment


# Swaper UI + API Tests

Two JUnit-Jupiter suites demonstrate a full sign-up flow
(**`FullRegistrationUiTest`**) and an API smoke check
(**`AccountApiTest`**).  
You can run them either on your workstation or fully head-less in Docker.

---
## Docker run, just run these two commands

docker build -t swaper-tests .

docker run --rm swaper-tests


## Prerequisites (local run)

* **Java 11+**  
* **Maven 3.8+**  
* **Google Chrome** >= 137 and its matching **chromedriver** in your `PATH`  
  (or export `CHROME_BIN` / `CHROME_DRIVER` to point at custom paths).

```bash
# fetch deps once
mvn -B dependency:go-offline

# run everything
mvn test
