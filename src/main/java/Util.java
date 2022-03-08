import java.math.BigInteger;

public class Util {

    static final BigInteger TWO = new BigInteger("2");
    static final BigInteger THREE = new BigInteger("3");

    /**
     * Hàm tính căn bậc 3 - BigInteger
     * @param n
     * @return
     */

    public static BigInteger cbrt(BigInteger n) {
        BigInteger guess = n.divide(BigInteger.valueOf((long) n.bitLength() / 3));
        boolean go = true;
        int c = 0;
        BigInteger test = guess;
        while (go) {
            BigInteger numOne = n.divide(guess.multiply(guess));
            BigInteger numTwo = guess.multiply(TWO);
            guess = numOne.add(numTwo).divide(THREE);
            if (numOne.equals(numTwo)) {
                go = false;
            }
            if (guess.mod(TWO).equals(BigInteger.ONE)) {
                guess = guess.add(BigInteger.ONE);
            }
            c++;
            c %= 5;
            if (c == 4 && (test.equals(guess))) {
                return guess;
            }
            if (c == 2) {
                test = guess;
            }
            if (c == 3) {
                guess = guess.add(BigInteger.ONE);
            }
        }

        return guess.add(BigInteger.ONE);

    }
}
