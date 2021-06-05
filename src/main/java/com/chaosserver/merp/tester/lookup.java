package com.chaosserver.merp.tester;

import com.chaosserver.merp.data.FileNameGetter;
import com.chaosserver.data.JavaBeanLoader;
import com.chaosserver.data.JavaBeanLoaderExceptionX;
import com.chaosserver.data.Lookup;

public class lookup {
    public static void main(String args[]) {
        System.out.println("Loading lookup");
        Lookup lp = null;
        try {
            lp = (Lookup) JavaBeanLoader.getBean(FileNameGetter.XML_STAT_BONUS_LOOKUP);
        }
        catch (JavaBeanLoaderExceptionX e) {
            System.out.println(e);
        }

        System.out.println("Size: " + lp.size());
        System.out.println("10 : " + lp.get(new Integer(19)));
    }
}