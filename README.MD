
# SpyderText Java SDK

## Building
```bash
./gradlew build
```
After building, .jar will be in build/lib/spydertext-sdk-{version}.jar

## Dependencies
The build process does not add dependencies into the jar, you will need to 
add the following dependencies to your project in addition to this jar:
```
    org.json
    org.apache.httpcomponents.httpclient
```