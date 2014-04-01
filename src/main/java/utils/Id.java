package utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author elf
 */
public class Id {

    private static final int ID_LENGTH = 10;

    public static String generateUniqueId() {
        return RandomStringUtils.randomNumeric(ID_LENGTH);
    }

}
