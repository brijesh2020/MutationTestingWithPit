# AgentX: Java Testing, Code Coverage & Mutation Testing Template

A battle-tested reference template for Java projects demonstrating how to set up and configure **JaCoCo (Code Coverage)**, **PIT / pitest (Mutation Testing)**, **JUnit 5 (Jupiter)**, and **Cucumber (BDD / Gherkin)**.

---

## 📋 Table of Contents
- [Overview](#-overview)
- [Why Mutation Testing? (JaCoCo vs. PIT)](#-why-mutation-testing-jacoco-vs-pit)
- [Project Structure](#-project-structure)
- [Quick Start](#-quick-start)
- [JaCoCo Setup & Usage](#-jacoco-setup--usage)
- [PIT Mutation Testing Setup & Usage](#-pit-mutation-testing-setup--usage)
- [How to Interpret the Reports](#-how-to-interpret-the-reports)
- [Adopting This Template in Your Own Project](#-adopting-this-template-in-your-own-project)
- [Common Pitfalls & Troubleshooting](#-common-pitfalls--troubleshooting)

---

## 🎯 Overview

High code coverage numbers can create a false sense of security if tests lack assertions. This template demonstrates:
- **Unit Testing**: Structured JUnit 5 test classes with `@Nested` suites and descriptive names.
- **BDD Testing**: Cucumber feature files with step definitions.
- **Code Coverage**: Automated JaCoCo HTML/XML report generation on every build.
- **Mutation Testing**: Automated PIT analysis verifying test suite effectiveness and assertion strength.

---

## 🔬 Why Mutation Testing? (JaCoCo vs. PIT)

| Feature | JaCoCo (Code Coverage) | PIT (Mutation Testing) |
| :--- | :--- | :--- |
| **Question Answered** | *"Did my tests execute this line of code?"* | *"Will my tests fail if someone breaks this code?"* |
| **How it Works** | Monitors bytecode execution during test runs. | Injects small faults (mutants) into bytecode (e.g., changes `+` to `-`, flips `if (x == y)` to `if (x != y)`, replaces return values with `0`/`null`) and checks if at least one test fails. |
| **Failure Mode** | Can achieve 100% coverage with zero assertions (`assert`). | Exposes tests with missing, weak, or useless assertions as **surviving mutants**. |

---

## 📁 Project Structure

```text
agentX/
├── pom.xml                                      # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/example/
│   │           └── Calculator.java              # Production code under test
│   └── test/
│       ├── java/
│       │   └── org/example/
│       │       ├── CalculatorTest.java          # JUnit 5 Unit Tests with @Nested structure
│       │       ├── CalculatorTestRunner.java    # JUnit Platform Suite Runner for Cucumber
│       │       └── CalculatorStepDefinitions.java # Cucumber step definitions
│       └── resources/
│           └── cucumber/
│               ├── calculator.feature           # Gherkin feature scenarios
│               └── cucumber.properties          # Cucumber runtime settings
```

---

## 🚀 Quick Start

### 1. Run Unit Tests & Generate JaCoCo Coverage
```powershell
mvn test
```
* Generates JaCoCo report at: `target/site/jacoco/index.html`

### 2. Run PIT Mutation Testing
```powershell
mvn test-compile pitest:mutationCoverage
```
* Generates PIT report at: `target/pit-reports/index.html`

---

## 📊 JaCoCo Setup & Usage

### Maven Configuration (`pom.xml`)

To ensure JaCoCo attaches its runtime agent without overriding Surefire settings, configure `maven-surefire-plugin` with `@{argLine}`:

```xml
<!-- Surefire Plugin -->
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.2.2</version>
  <configuration>
    <!-- @{argLine} dynamically injects the JaCoCo agent parameters -->
    <argLine>@{argLine} -Dfile.encoding=UTF-8</argLine>
  </configuration>
</plugin>

<!-- JaCoCo Plugin -->
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.12</version>
  <executions>
    <!-- Injects agent prior to test execution -->
    <execution>
      <goals>
        <goal>prepare-agent</goal>
      </goals>
    </execution>
    <!-- Automatically generates HTML report after tests complete -->
    <execution>
      <id>report</id>
      <phase>test</phase>
      <goals>
        <goal>report</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

### Useful JaCoCo Commands

| Task | Command |
| :--- | :--- |
| **Run tests & generate report** | `mvn test` |
| **Generate report only** *(after tests ran)* | `mvn jacoco:report` |

---

## 🧬 PIT Mutation Testing Setup & Usage

### Maven Configuration (`pom.xml`)

```xml
<plugin>
  <groupId>org.pitest</groupId>
  <artifactId>pitest-maven</artifactId>
  <version>1.16.1</version>
  <configuration>
    <jvmArgs>
      <jvmArg>-Dfile.encoding=UTF-8</jvmArg>
    </jvmArgs>
  </configuration>
  <dependencies>
    <!-- JUnit 5 Engine Adapter for PIT -->
    <dependency>
      <groupId>org.pitest</groupId>
      <artifactId>pitest-junit5-plugin</artifactId>
      <version>1.2.1</version>
    </dependency>
  </dependencies>
</plugin>
```

### Running PIT Commands

#### Run on entire project
```powershell
mvn test-compile pitest:mutationCoverage
```

#### Run on a specific test class
```powershell
mvn test-compile pitest:mutationCoverage "-DtargetTests=org.example.CalculatorTest"
```

#### Target specific production classes to mutate
```powershell
mvn test-compile pitest:mutationCoverage "-DtargetClasses=org.example.Calculator" "-DtargetTests=org.example.CalculatorTest"
```

#### Exclude slow or integration test classes
```powershell
mvn test-compile pitest:mutationCoverage "-DexcludedTestClasses=org.example.*IT,org.example.*SlowTest"
```

### Running from IntelliJ IDEA
1. Open **Run Anything** (`Ctrl` + `Ctrl`) or click **Execute Maven Goal** (`m` icon in Maven sidebar).
2. Enter:
   ```text
   test-compile pitest:mutationCoverage -DtargetTests=org.example.CalculatorTest
   ```
   *(Note: Do not wrap flags in quotes when using IntelliJ's Maven dialog).*

---

## 📈 How to Interpret the Reports

### JaCoCo Report (`target/site/jacoco/index.html`)
- **Instructions / Line Coverage**: The percentage of bytecode instructions and source lines executed by tests.
- **Branch Coverage**: Checks whether all boolean decision branches (`if`, `else`, `switch`, loops) were traversed in both true and false directions.

### PIT Mutation Report (`target/pit-reports/index.html`)
- **Line Coverage**: % of lines executed by tests during PIT discovery.
- **Mutation Coverage**: `(Killed Mutants / Total Generated Mutants) * 100`.
- **Test Strength**: `(Killed Mutants / Mutants Covered by Tests) * 100`. Tests that execute code without asserting behavior will show low test strength.

#### Mutation States:
- **Killed (Good)**: A mutant was injected, and at least one test failed as a result.
- **Survived (Bad)**: A mutant was injected, but all tests still passed. Indicates missing assertions or edge cases.
- **No Coverage**: No test touched this line of code.
- **Timed Out**: A mutant caused an infinite loop or hang (treated as killed).

---

## 🛠 Adopting This Template in Your Own Project

To introduce this setup to any Maven project:

1. **Ensure JUnit 5 Dependency Compatibility**:
   Ensure `junit-jupiter`, `junit-platform-suite-api`, and `pitest-junit5-plugin` versions are aligned.
   ```xml
   <properties>
     <junit.jupiter.version>5.10.2</junit.jupiter.version>
     <junit.platform.version>1.10.2</junit.platform.version>
     <pitest.version>1.16.1</pitest.version>
     <pitest.junit5.version>1.2.1</pitest.junit5.version>
   </properties>
   ```

2. **Add Plugins**:
   Copy the `maven-surefire-plugin`, `jacoco-maven-plugin`, and `pitest-maven` configurations from this project's [`pom.xml`](pom.xml).

3. **Verify CI/CD Integration**:
   Add a mutation threshold in `pom.xml` to fail builds if test quality drops below a required percentage:
   ```xml
   <configuration>
     <mutationThreshold>80</mutationThreshold>
     <coverageThreshold>85</coverageThreshold>
   </configuration>
   ```

---

## ⚠️ Common Pitfalls & Troubleshooting

### 1. `Coverage generator Minion exited abnormally (UNKNOWN_ERROR)`
- **Cause**: Incompatible versions of `junit-platform-commons` / `junit-jupiter` / `pitest-junit5-plugin` on the test classpath.
- **Fix**: Keep JUnit 5 (`5.10.2`) and JUnit Platform (`1.10.2`) versions matching.

### 2. PIT reports `0 tests discovered` with `@Nested` classes
- **Cause**: In JUnit 5 Jupiter, `@Nested` classes must be **non-static** (`class AddTests { ... }`). Declaring them as `public static class` causes Jupiter's test discoverer to skip them.
- **Fix**: Remove the `static` modifier from inner `@Nested` classes.

### 3. JaCoCo reports `Skipping JaCoCo execution due to missing execution data file`
- **Cause**: Running `mvn jacoco:report` before `mvn test` has executed and generated `target/jacoco.exec`.
- **Fix**: Run `mvn test` (which triggers both `prepare-agent` and `report` automatically).

### 4. `The syntax of the command is incorrect` on Windows / PowerShell
- **Cause**: Copying IntelliJ internal CLI arguments with unquoted spaces (e.g. `C:\Program Files\...`).
- **Fix**: Pass arguments cleanly:
  ```powershell
  mvn test-compile pitest:mutationCoverage "-DtargetTests=org.example.CalculatorTest"
  ```
