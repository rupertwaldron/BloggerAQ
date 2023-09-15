package com.ruppyrup.qa.runners.beanrunner;


import com.ruppyrup.qa.runners.CukeAnnotation;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@CukeAnnotation
@IncludeTags({"SpringBean"})
public class SpringBeanRunner {
}
