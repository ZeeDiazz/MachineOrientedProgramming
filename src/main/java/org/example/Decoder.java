package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Decoder {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
            System.out.printf("Enter 1 to execute the program: \n0 - LC3, 1 - AscciToHex, 2- TwoComplementToDec, 3 - AddTwoComplement \n4 - DecToBinary, 5 - UnsignedBinaryToDecimal, 6 - AddUnsigned,  7 - SubtractUnsigned \n8 - DecToHex, 9 - AddHex%n");

            String input = reader.readLine();
            switch (input) {
                case "0" -> {
                    System.out.println("Bin to LC3:");
                    LC3simple();
                }
                case "1" -> {
                    System.out.println("ASCCITOHEX");
                    asciiToHex();
                }
                case "2" -> {
                    System.out.println("2's complement to Bin:");
                    twosComplementBinaryToDecimal();
                }
                case "3" -> AddTwoComplement();
                case "4" -> {
                    System.out.println("Write the decimal:");
                    decimalToBinary();//doesn't work with negative num
                }
                case "5" -> {
                    System.out.println("Write UnsignedBin:");
                    //work with positive num & doesn't work with negative
                    UnsignedBinToDec();
                    //Works with negative num  & doesn't work with negative
                    //binaryToDecimal();
                }
                case "6" -> AddUnsignedBin();
                case "7" -> subtractUnsignedBin();
                case "8" -> decToHex();
                case "9" -> addHex();
                default -> System.out.println("Program not executed.");
            }
    }
    public static void binaryToDecimal() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            if (!line.matches("[01]+")) {
                throw new IllegalArgumentException("Invalid binary number: " + line);
            }

            int decimal;
            if (line.charAt(0) == '0') {
                // Positive number, convert directly
                decimal = Integer.parseInt(line, 2);
            } else {
                // Negative number, convert to two's complement
                StringBuilder sb = new StringBuilder();
                for (char bit : line.toCharArray()) {
                    sb.append(bit == '0' ? '1' : '0');
                }
                String inverted = sb.toString();
                decimal = -(Integer.parseInt(inverted, 2) + 1);
            }

            System.out.println(decimal);
        }
    }
    public static void twosComplementBinaryToDecimal() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            if (line.matches("[01]+")) {
                int decimal;
                if (line.charAt(0) == '1') {
                    // convert to two's complement
                    String twosComplement = "";
                    for (int i = 0; i < line.length(); i++) {
                        twosComplement += (line.charAt(i) == '0') ? '1' : '0';
                    }
                    decimal = -1 * (Integer.parseInt(twosComplement, 2) + 1);
                } else {
                    decimal = Integer.parseInt(line, 2);
                }
                System.out.println(decimal);
            } else {
                System.err.println("Invalid binary string: " + line);
            }
        }
    }
    public static void UnsignedBinToDec() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            if (line.matches("[01]+")) {
                int decimal = Integer.parseInt(line, 2);
                System.out.println(decimal);
            } else {
                System.err.println("Invalid binary string: " + line);
            }
        }
    }
    public static void decToHex() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            if (line.matches("\\d+")) {
                int decimal = Integer.parseInt(line);
                String hex = Integer.toHexString(decimal);
                System.out.println(hex);
            } else {
                System.err.println("Invalid decimal number: " + line);
            }
        }
    }
     public static void addHex() throws IOException {
         System.out.println("Enter the first Hex number:");
         String hex1 = reader.readLine();
         System.out.println("Enter the second Hex number:");
         String hex2 = reader.readLine();

        int decimal1 = Integer.parseInt(hex1, 16); // Convert hex1 to decimal
         int decimal2 = Integer.parseInt(hex2, 16); // Convert hex2 to decimal

         int sum = decimal1 + decimal2; // Add the decimal numbers

         String result = Integer.toHexString(sum); // Convert the sum back to hexadecimal

         System.out.println("Sum of " + hex1 + " and " + hex2 + " is " + result.toString());
    }
    public static void decimalToBinary() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            try {
                int decimal = Integer.parseInt(line);
                String binary = "";
                if (decimal < 0) {
                    // convert negative decimal to binary using two's complement representation
                    int mask = 1 << 31; // create a mask with the leftmost bit set to 1
                    for (int i = 0; i < 32; i++) {
                        if ((decimal & mask) == 0) {
                            binary += "0";
                        } else {
                            binary += "1";
                        }
                        decimal <<= 1; // left shift the decimal number
                    }
                } else {
                    // convert positive decimal to binary using built-in method
                    for (int i = 0; i < 32-line.length(); i++) {
                        binary += "0";
                    }
                    binary = Integer.toBinaryString(decimal);
                }
                System.out.println(binary);
            } catch (NumberFormatException e) {
                System.err.println("Invalid decimal number: " + line);
            }
        }
    }

    public static void AddTwoComplement() throws IOException {

            System.out.println("Enter the first binary number:");
            String binary1 = reader.readLine();
            System.out.println("Enter the second binary number:");
            String binary2 = reader.readLine();
            int len = Math.max(binary1.length(), binary2.length());
            // Pad zeros to make both strings of equal length
            binary1 = padZeros(binary1, len);
            binary2 = padZeros(binary2, len);

            StringBuilder result = new StringBuilder();
            int carry = 0;

            for (int i = len - 1; i >= 0; i--) {
                int bit1 = binary1.charAt(i) - '0';
                int bit2 = binary2.charAt(i) - '0';

                int sum = (bit1 ^ bit2 ^ carry) + '0';

                result.insert(0, (char) sum);

                carry = (bit1 & bit2) | (bit2 & carry) | (bit1 & carry);
            }

            // Overflow check

             if (carry == 1) {
                result.insert(0, '1');
            }

            System.out.println("Sum of " + binary1 + " and " + binary2 + " is " + result.toString());
    }

    private static String padZeros(String binary, int len) {
        StringBuilder sb = new StringBuilder(binary);
        while (sb.length() < len) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }


    public static void AddUnsignedBin() throws IOException {

        while (true) {
            System.out.println("Enter the first binary number:");
            String binary1 = reader.readLine();
            System.out.println("Enter the second binary number:");
            String binary2 = reader.readLine();
            int len = Math.max(binary1.length(), binary2.length());

            // Pad zeros to make both strings of equal length
            binary1 = padZeros(binary1, len);
            binary2 = padZeros(binary2, len);

            StringBuilder result = new StringBuilder();
            int carry = 0;

            for (int i = len - 1; i >= 0; i--) {
                int bit1 = binary1.charAt(i) - '0';
                int bit2 = binary2.charAt(i) - '0';

                int sum = (bit1 + bit2 + carry) % 2 + '0';

                result.insert(0, (char) sum);

                carry = (bit1 + bit2 + carry) / 2;
            }

            // Overflow check
            if (carry == 1) {
                result.insert(0, '1');
            }

            System.out.println("Sum of " + binary1 + " and " + binary2 + " is " + result.toString());
        }
    }
    public static void subtractUnsignedBin() throws IOException {
        while (true) {
            System.out.println("Enter the first binary number:");
            String binary1 = reader.readLine();
            System.out.println("Enter the second binary number:");
            String binary2 = reader.readLine();
            int len = Math.max(binary1.length(), binary2.length());

            // Pad zeros to make both strings of equal length
            binary1 = padZeros(binary1, len);
            binary2 = padZeros(binary2, len);

            StringBuilder result = new StringBuilder();
            int borrow = 0;

            for (int i = len - 1; i >= 0; i--) {
                int bit1 = binary1.charAt(i) - '0';
                int bit2 = binary2.charAt(i) - '0';

                int diff = (bit1 - bit2 - borrow + 2) % 2;

                result.insert(0, (char) (diff + '0'));

                borrow = (bit1 - bit2 - borrow < 0) ? 1 : 0;
            }

            System.out.println("Difference of " + binary1 + " and " + binary2 + " is " + result.toString());
        }
    }


    public static void LC3simple() throws IOException {
        String line;
        boolean processedInput = false;
        while ((line = reader.readLine()) != null) {
            line = line.trim(); // trim the input string
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            if (line.matches("[01]{16}")) {
                try {
                    LC3Instruction instruction = LC3Instruction.decode(line);
                    String opcode = instruction.opcode.toString();
                    String dr = instruction.dr == 40000 ? "" : "R" + instruction.dr;
                    String sr1 = instruction.sr1 == 40000 ? "" : ", R" + instruction.sr1;
                    String sr2 = instruction.sr2 == 40000 ? "" : ", R" + instruction.sr2;
                    String imm5value = instruction.imm5value == 40000 ? "" : ", #" + instruction.imm5value;
                    String offset = instruction.offset == 40000 ? "" : ", #" + instruction.offset;
                    String baseR = instruction.baseR == 40000 ? "" : ", R" + instruction.baseR;
                    String trapvect8 = instruction.trapvect8 == 40000 ? "" : ", #" + instruction.trapvect8;
                    String trapMessage = instruction.trapMessage == null ? "" : ", \"" + instruction.trapMessage + "\"";
                    if (opcode.equals("BR")) {
                        // Handle BR instruction
                        boolean n = line.charAt(4) == '1';
                        boolean z = line.charAt(5) == '1';
                        boolean p = line.charAt(6) == '1';
                        if (n) {
                            dr += "n";
                        } if (z){
                            sr1 += "z";
                        } if (p){
                            sr2 += "p";
                        }
                        baseR = "";

                    }
                    else if (opcode.equals("TRAP")) {
                        opcode = "";
                        trapvect8 = "";
                        if(trapMessage.contains(",")){
                            trapMessage = trapMessage.replace(",", "");
                            trapMessage = trapMessage.replace(" ", "");
                        }

                    }
                    System.out.println(opcode + " " + dr + sr1 + sr2 + imm5value + offset + baseR + trapvect8 + trapMessage);
                    processedInput = true;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid binary string: " + line);
                }
            } else {
                System.err.println("Invalid binary string: " + line);
            }
        }
        if (!processedInput) {
            System.out.println(); // print an empty line if no input was processed
        }
    }
    public static void asciiToHex() throws IOException {
        String ascii = reader.readLine();
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < ascii.length(); i++) {
            char c = ascii.charAt(i);
            String hexChar = String.format("%02X", (int) c);
            hex.append(hexChar);
        }
        System.out.println(hex.toString());
    }

}