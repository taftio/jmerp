package com.chaosserver.merp.tester.data;

import com.chaosserver.merp.data.FileNameGetter;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataFileTester extends TestCase {
    public DataFileTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DataFileTester.class);

        return suite;
    }

    protected ArrayList myFiles = new ArrayList();

    @Override
    protected void setUp() {
        myFiles.add(new File(FileNameGetter.XML_RACELIST));
        myFiles.add(new File(FileNameGetter.XML_STATLIST));
    }

    public void testData() {
        System.out.println("DataFileTester.testData()");
        Iterator myIter = myFiles.iterator();

        while(myIter.hasNext()) {
            File f = (File) myIter.next();
//            assert( "No File: " + f.getAbsolutePath(), f.exists() );
        }

    }

}
