package jp.vmi.junit.result;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import static jp.vmi.junit.result.ObjectFactory.*;

/**
 * testcase element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "skipped",
    "error",
    "failure",
    "systemOut",
    "systemErr"
})
public class TestCaseResult extends TestResult {

    private static final String NL = System.getProperty("line.separator");

    @XmlTransient
    private final List<String> systemOuts = new ArrayList<String>();

    @XmlTransient
    private final List<String> systemErrs = new ArrayList<String>();

    @XmlTransient
    private boolean success = false;

    @XmlElement
    private Error error = null;

    @XmlElement
    private Failure failure = null;

    /**
     * Add system-out message.
     *
     * @param message system-out message.
     */
    public void addSystemOut(String message) {
        synchronized (systemOuts) {
            systemOuts.add(message);
        }
    }

    /**
     * Add system-err message.
     *
     * @param message system-err message.
     */
    public void addSystemErr(String message) {
        synchronized (systemErrs) {
            systemErrs.add(message);
        }
    }

    /**
     * Set success result.
     */
    public void setSuccess() {
        this.success = true;
    }

    /**
     * Set error result.
     *
     * @param message error message.
     * @param value error value.
     */
    public void setError(String message, String value) {
        error = factory.createError(message, value);
    }

    /**
     * Set failure result.
     *
     * @param message failure message.
     * @param value failure value.
     */
    public void setFailure(String message, String value) {
        failure = factory.createFailure(message, value);
    }

    /**
     * End test-case.
     */
    public void endTestCase() {
        endTest();
    }

    /**
     * Get passed count.
     *
     * @return passed count. (0 or 1)
     */
    public int getPassed() {
        return success ? 1 : 0;
    }

    /**
     *  Get error count.
     *
     *  @return error count. (0 or 1)
     */
    public int getErrors() {
        return error != null ? 1 : 0;
    }

    /**
     *  Get failure count.
     *
     *  @return failure count. (0 or 1)
     */
    public int getFailures() {
        return failure != null ? 1 : 0;
    }

    /**
     * Get skipped count.
     *
     * @return skipped count.
     */
    @XmlElementRef
    @XmlJavaTypeAdapter(SkippedAdapter.class)
    public Integer getSkipped() {
        return (!success && error == null && failure == null) ? 1 : 0;
    }

    /**
     * Get system-out message.
     *
     * @return system-out message.
     */
    @XmlElement(name = "system-out")
    public String getSystemOut() {
        return (systemOuts.size() != 0) ? StringUtils.join(systemOuts, NL) : null;
    }

    /**
     * Get system-err message.
     *
     * @return system-err message.
     */
    @XmlElement(name = "system-err")
    public String getSystemErr() {
        return (systemErrs.size() != 0) ? StringUtils.join(systemErrs, NL) : null;
    }

    public int getCommandPassed() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    public int getCommandFailures() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    public int getCommandErrors() {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    public List<CommandResult> getCommandResults() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}
