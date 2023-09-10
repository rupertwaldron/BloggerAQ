package com.ruppyrup;


import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ruppyrup.steps")
@IncludeTags({"Translation", "SpringBean"})
public class CucumberRunner {
}
