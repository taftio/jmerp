package com.chaosserver.merp.tester;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.merp.data.FileNameGetter;
import org.apache.log4j.PropertyConfigurator;


public class MerpTesterBase extends TestCase {
    static {
        // Setup the loggers
        PropertyConfigurator.configure(FileNameGetter.PROPERTIES_LOGGING_CONFIG);
    }
    public MerpTesterBase(String name) {
        super(name);
    }
}