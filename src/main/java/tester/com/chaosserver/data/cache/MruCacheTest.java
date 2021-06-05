package tester.com.chaosserver.data.cache;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.chaosserver.data.cache.MruCache;
import com.chaosserver.merp.tester.MerpTesterBase;

public class MruCacheTest extends MerpTesterBase {
    public MruCacheTest(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("MruCacheTest");
        suite.addTestSuite(MruCacheTest.class);
        return suite;
    }

    public void testMru() throws Exception {
        MruCache cache = new MruCache();

        cache.setMaxSize(5);
        cache.put("1", "1");
        Thread.sleep(500);
        cache.put("2", "2");
        Thread.sleep(500);
        cache.put("3", "3");
        Thread.sleep(500);
        cache.put("4", "4");
        Thread.sleep(500);
        cache.put("5", "5");

        Thread.sleep(500);
        cache.put("6", "6");

        if(cache.containsKey("1")) {
            fail("Cache should not contain key 1!");
        }

        Thread.sleep(500);
        cache.put("7", "7");

        if(cache.containsKey("2")) {
            fail("Cache should not contain key 2!");
        }

        cache.get("3");

        Thread.sleep(500);
        cache.put("8", "8");

        if(!cache.containsKey("3")) {
            fail("Cache should contain key 3!");
        }

        if(cache.containsKey("4")) {
            fail("Cache should not contain key 4!");
        }

    }

}
