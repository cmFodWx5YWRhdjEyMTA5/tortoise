package com.android.nobug.util;

import java.util.StringTokenizer;

public class VersionUtil {

    public static int compareVersionNameString(String curVersion, String otherVersion) {
        StringTokenizer curVerNumbers = new StringTokenizer(curVersion, ".");
        StringTokenizer otherVerNumbers = new StringTokenizer(otherVersion, ".");

        do {
            int compareValue = curVerNumbers.nextToken().compareToIgnoreCase(otherVerNumbers.nextToken());

            if(compareValue == 0) {
                continue;
            } else {
                return compareValue*-1;
            }
        }while(curVerNumbers.hasMoreElements() && otherVerNumbers.hasMoreElements());

        if(curVerNumbers.hasMoreElements()) {
            return -1;
        } else if(otherVerNumbers.hasMoreElements()) {
            return 1;
        }
        return 0;
    }

    public static boolean compareVersionCode(int current, int server) {
        return (current < server);
    }

//  ================================================================================================

}
