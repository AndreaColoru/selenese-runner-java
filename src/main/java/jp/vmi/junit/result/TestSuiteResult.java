package jp.vmi.junit.result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import static jp.vmi.junit.result.ObjectFactory.*;

/**
 * Result of test-suite.
 */
@XmlRootElement(name = "testsuite")
@XmlType(propOrder = {
    "properties",
    "error",
    "testCaseResults"
})
public class TestSuiteResult extends TestResult {

    @XmlAttribute
    private Integer id;

    @XmlAttribute
    private XMLGregorianCalendar timestamp;

    @XmlAttribute
    private BigDecimal time;

    @XmlElementWrapper
    @XmlElement(name = "property")
    private final List<Property> properties = new ArrayList<Property>();

    @XmlElement
    private TestCaseResult error;

    @XmlElement(name = "testcase")
    private final List<TestCaseResult> testCaseResults = new ArrayList<TestCaseResult>();

    /**
     * Add property.
     *
     * @param name property name.
     * @param value property value.
     */
    public void addProperty(String name, String value) {
        Property property = factory.createProperty(name, value);
        synchronized (properties) {
            properties.add(property);
        }
    }

    /**
     * Set error when the test class failed to load
     *
     * @param error error result instance.
     */
    public void setError(TestCaseResult error) {
        this.error = error;
    }

    /**
     * Add TestCaseResult instance.
     *
     * @param caseResult TestCaseResult instatnce.
     */
    public void addTestCaseResult(TestCaseResult caseResult) {
        synchronized (testCaseResults) {
            testCaseResults.add(caseResult);
        }
    }

    /**
     * End test-suite.
     */
    public void endTestSuite() {
        endTest();
    }

    /**
     * Get test count.
     *
     * @return test count.
     */
    @XmlAttribute
    public int getTests() {
        return testCaseResults.size();
    }

    /**
     * Get failure count.
     *
     * @return failure count.
     */
    @XmlAttribute
    public int getFailures() {
        int failures = 0;
        for (TestCaseResult result : testCaseResults)
            failures += result.getFailures();
        return failures;
    }

    /**
     * Get error count.
     *
     * @return error count.
     */
    @XmlAttribute
    public int getErrors() {
        int errors = 0;
        for (TestCaseResult result : testCaseResults)
            errors += result.getErrors();
        return errors;
    }

    /**
     * Get skipped count.
     *
     * @return skipped count.
     */
    @XmlAttribute
    public int getSkipped() {
        int skipped = 0;
        for (TestCaseResult result : testCaseResults)
            skipped += result.getSkipped();
        return skipped;
    }

    /**
     * Get passed count.
     *
     * @return passed count.
     */
    public int getPassed() {
        int passed = 0;
        for (TestCaseResult result : testCaseResults)
            passed += result.getPassed();
        return passed;
    }
}
