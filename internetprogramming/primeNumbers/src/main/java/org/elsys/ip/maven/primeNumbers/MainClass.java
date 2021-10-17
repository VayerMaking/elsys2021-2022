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

                if(parsedI <= 0){
                    flag = true;
                }

                if (!flag)
                    System.out.println(parsedI + " is a prime");
                else
                    System.out.println(parsedI + " is not a prime");

            }catch (NumberFormatException e){



                boolean number = true;
                boolean integer = true;

                try {
                    float num = Float.parseFloat(i);
                } catch (NumberFormatException ee) {
                    integer = false;
                }

                if(integer)
                    if (Float.parseFloat(i) <= Integer.MIN_VALUE || Float.parseFloat(i) >= Integer.MAX_VALUE){
                        System.out.println(i + " is out of bound");
                    }else {
                        System.out.println(i + " is not an integer");
                    }
                else
                    System.out.println(i + " is not a number");

            }
            }
        }
    }

