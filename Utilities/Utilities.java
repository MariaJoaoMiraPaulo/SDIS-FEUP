package Utilities;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Utilities{

    /**
     * Returns a hexadecimal encoded SHA-256 hash for the input String.
     *
     * @param data
     * @return string with Hash
     */
    public static byte[] createHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return hash;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
     * to a hexadecimal string. Note that this generates hexadecimal in upper case.
     *
     * @param hash
     * @return string with hash in hexadecimal
     */
    private static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }

    public static BigInteger getBigInteger(byte[] hash){
        return new BigInteger( hash );
    }
}
