# Santorini

## Run Executable

```bash
tar -xvf executable.tar.gz
./runable.sh
```

## Run Jar Directly

```bash
tar -xvf executable.tar.gz
java -jar standalone.jar
```

## Build

```bash
lein clean; lein compile; lein uberjar
cp target/uberjar/santorini-0.1.0-SNAPSHOT-standalone.jar ./standalone.jar && tar -cvzf executable.tar.gz runable.sh standalone.jar && rm standalone.jar
```

## Run Using Lein

`lein run`

## Run Tests

`lein test`
