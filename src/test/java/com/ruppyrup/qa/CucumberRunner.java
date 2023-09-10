package com.ruppyrup.qa;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.ruppyrup.qa")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ruppyrup.qa")
@IncludeTags({"Translation", "SpringBean"})
public class CucumberRunner {
}
