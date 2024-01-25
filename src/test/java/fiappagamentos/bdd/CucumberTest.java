package fiappagamentos.bdd;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
        @ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "fiappagamentos.bdd"),
})
public class CucumberTest {

}
