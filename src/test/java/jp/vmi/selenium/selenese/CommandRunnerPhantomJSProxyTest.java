package jp.vmi.selenium.selenese;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.Verifier;

import jp.vmi.selenium.testutils.WebProxyResource;

import jp.vmi.selenium.webdriver.DriverOptions;
import jp.vmi.selenium.webdriver.DriverOptions.DriverOption;
import jp.vmi.selenium.webdriver.WebDriverManager;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Test for PhantomJS with proxy.
 */
public class CommandRunnerPhantomJSProxyTest extends CommandRunnerPhantomJSTest {
    /**
     * proxy resource
     */
    @ClassRule
    public static WebProxyResource proxy = new WebProxyResource();

    /**
     * verify used proxy in testmethod.
     */
    @Rule
    public Verifier proxyused = new Verifier() {
        @Override
        protected void verify() throws Throwable {
            assertThat(proxy.getProxy().getCount(), is(greaterThan(0)));
        }
    };

    @Override
    protected void setupWebDriverManager() {
        super.setupWebDriverManager();
        WebDriverManager manager = WebDriverManager.getInstance();
        DriverOptions opts = manager.getDriverOptions();
        opts.set(DriverOption.PROXY, proxy.getProxy().getServerNameString());
    }

}
