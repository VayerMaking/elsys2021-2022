package org.elsys.ip.maven.primeNumbers;

public class MainClass {
    public static void main (String args[]){
        for ( String i : args) {
            try {
                int parsedI = Integer.parseInt(i);
                boolean flag = false;

                for (int j = 2; j <= parsedI / 2; ++j) {
                    if (parsedI % j == 0) {
                        flag = true;
                        break;
                    }
                }

                if (!flag)
                    System.out.println(parsedI + " is a prime number.");
                else
                    System.out.println(parsedI + " is not a prime number.");

            }catch (NumberFormatException e){
                System.out.println(i + " is not a number");
            }
        }
    }
}
