package com.ruppyrup.qa.runners.beanrunner;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;


/**
 * @Suite - annotation from JUnit 5 to make this class a run configuration for test suite.
 * @IncludeEngines("cucumber") - tells JUnit 5 to use Cucumber test engine to run features.
 * @SelectClasspathResource("features") - to change the location of your feature files (if you do not add this annotation classpath of the current class will be used).
 * @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.mycompany.cucumber") - this annotation specifies the path to steps definitions (java classes).
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ruppyrup.qa")
@IncludeTags({"Translation", "SpringBean"})
public class CucumberRunner {
}
