import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Factor {

    static BigInteger number;
    static BigInteger sqrt;
    static BigInteger cbrt;
    static boolean abbrev;
    static final BigInteger TWO = new BigInteger("2");
    static final BigInteger THREE = new BigInteger("3");
    static int[] arrayData;
    static BigInteger pArray;

    public static void main(String[] args) {
        LoadSysData();
        abbrev = ask("Do you want to abbreviate numbers? ");
        try {
            number = new BigInteger(getFile());
        } catch (IOException ex) {
            System.out.println("Error: File not found");
            ;
        }
        System.out.println("Factoring: \n\n" + abbreviate(number));
        sqrt = sqrt(number);
        System.out.println("The square root is: " + abbreviate(sqrt));
        BigInteger testNum = sqrt.multiply(sqrt);
        System.out.println("Difference: " + abbreviate(number.subtract(testNum).abs()));
        System.out.println("Check returns: " + checksqrt());
        cbrt = cbrt(number);
        testNum = cbrt.multiply(cbrt).multiply(cbrt);
        System.out.println("The cube root is: " + abbreviate(cbrt));
        System.out.println("Difference: " + abbreviate(number.subtract(testNum).abs()));
        System.out.println("Check returns: " + checksqrt());
        testNum = sqrt.subtract(cbrt);
        System.out.println("Search size: " + abbreviate(testNum));
        arrayData = getLargestArray();
        System.out.println("Prime stats determined at " + arrayData[0] + " dimension(s) of size(s) " + getArrayData());
        System.out.println(FreeRAM() + " of RAM remaining.");
        System.out.println("Beginning prime load...\tExpect heavy RAM/CPU usage.");
        IntializeArray();
        System.out.println(FreeRAM() + " of RAM remaining.");

    }

    public static String getFile() throws IOException {
        Scanner scanner = new Scanner(new File("Number.dat"));
        StringBuilder content = new StringBuilder();
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine());
        }
        return content.toString();
    }

    public static BigInteger sqrt(BigInteger n) {
        BigInteger guess = n.divide(BigInteger.valueOf((long) n.bitLength() / 2));
        boolean go = true;
        int c = 0;
        BigInteger test = guess;
        while (go) {
            BigInteger numOne = guess.divide(TWO);
            BigInteger numTwo = n.divide(guess.multiply(TWO));
            guess = numOne.add(numTwo);
            if (numOne.equals(numTwo)) {
                go = false;
            }
            if (guess.mod(TWO).equals(BigInteger.ONE)) {
                guess = guess.add(BigInteger.ONE);
            }
            //System.out.println(guess.toString());
            c++;
            c %= 5;
            if (c == 4 && (test.equals(guess))) {
                return guess;
            }
            if (c == 2) {
                test = guess;
            }
        }

        if ((guess.multiply(guess)).equals(number)) {
            return guess;
        }
        return guess.add(BigInteger.ONE);

    }

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
            //System.out.println(guess.toString());
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

        if ((guess.multiply(guess)).equals(number)) {
            return guess;
        }
        return guess.add(BigInteger.ONE);

    }

    public static int[] getLargestArray() {
        ArrayList<Object> test = new ArrayList<Object>();
        int x = 1, y = 1, z = 1, a = 1, b = 1;
        int scale = 100000;
        int d = 1;
        boolean go = true;
        while (go) {
            try {
                switch (d) {

                    case 1:
                        test.add(new BigInteger[x]);
                        x += scale;
                        break;
                    case 2:

                        test.add(new BigInteger[x][y]);
                        y += scale;
                        break;

                    case 3:
                        test.add(new BigInteger[x][y][z]);
                        z += scale;
                        break;
                    case 4:
                        test.add(new BigInteger[x][y][z][a]);
                        a += scale;
                        break;
                    case 5:
                        test.add(new BigInteger[x][y][z][a][b]);
                        b += scale;
                        break;
                    default:
                        test.add(new BigInteger[x]);

                }
                if (x == Integer.MAX_VALUE) {
                    d++;
                }
                if (y == Integer.MAX_VALUE) {
                    d++;
                }
                if (z == Integer.MAX_VALUE) {
                    d++;
                }
                if (a == Integer.MAX_VALUE) {
                    d++;
                }
                if (b == Integer.MAX_VALUE) {
                    d++;
                }
                test.clear();
            } catch (OutOfMemoryError e) {
                scale %= 10;
                //System.out.println(d + " " + scale + " " + x);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (scale == 0) {
                go = false;
            }
        }
        int[] results = new int[6];
        results[0] = d;
        results[1] = x;
        results[2] = y;
        results[3] = z;
        results[4] = a;
        results[5] = b;
        return results;
    }

    public static boolean checksqrt() {
        if ((sqrt.multiply(sqrt)).equals(number)) {
            return true;
        }
        BigInteger margin = number.subtract((sqrt.multiply(sqrt))).abs();
        BigInteger maxError = (sqrt.subtract(BigInteger.ONE)).multiply(TWO);
        if (margin.compareTo(maxError) == -1) {
            return true;
        }
        return false;

    }

    public static boolean checkcbrt() {
        if ((cbrt.multiply(cbrt).multiply(cbrt)).equals(number)) {
            return true;
        }
        BigInteger margin = number.subtract((cbrt.multiply(cbrt).multiply(cbrt))).abs();
        BigInteger c = cbrt.subtract(BigInteger.ONE);
        BigInteger maxError = ((c.multiply(c)).multiply(THREE)).add(c.multiply(THREE));
        if (margin.compareTo(maxError) == -1) {
            return true;
        }
        return false;

    }

    public static String abbreviate(BigInteger n) {
        if (n.toString().length() < 7) {
            return n.toString();
        }
        if (abbrev) {
            return n.toString().substring(0, 3) + "..." + n.mod(new BigInteger("1000")) + "(" + n.toString().length() + " digits)";
        }
        return n.toString();
    }

    public static boolean ask(String prompt) {
        Scanner s = new Scanner(System.in);
        System.out.println(prompt + "(Y/N)");
        String input = s.nextLine();
        char c;
        if (input.length() != 0) {
            c = input.charAt(0);
        } else {
            return ask("");
        }
        if (c == 'N' || c == 'n') {
            return false;
        }
        if (c == 'Y' || c == 'y') {
            return true;
        }
        return ask("");
    }

    public static String getArrayData() {
        String result = "";
        for (int i = 0; i < arrayData[0]; i++) {
            result = result += arrayData[i + 1] + ",";
        }
        return result.substring(0, result.length() - 2);
    }

    public static String FreeRAM() {
        long bytes = Runtime.getRuntime().freeMemory();
        if (bytes > 1073741823) {
            return String.format("%.4f GB", (bytes / 1073741824.0));
        }
        if (bytes > 1048575) {
            return String.format("%.4f MB", (bytes / 1048576.0));
        }
        if (bytes > 1023) {
            return String.format("%.4f KB", (bytes / 1024.0));
        }
        return bytes + " bytes";
    }

    public static void IntializeArray() {
        switch (arrayData[0]) {

            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:

        }
        //Figure out way to assign n-dimensional array to a static object
    }

    public static void LoadSysData() {
        System.out.println(FreeRAM());
        System.out.println("Number of cores: " + Runtime.getRuntime().availableProcessors());
    }

}