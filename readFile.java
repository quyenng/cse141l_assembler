import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


public class readFile {
    public static HashMap<Integer, Integer> jumps = new HashMap<Integer, Integer>();

    public static String machCode (String [] args) {
        String opcode = "";
        String arg_a = "";
        String arg_b = "";

        int temp;


        //opcodes
        switch (args[0]) {
            case "Str":
                opcode = "000";
                break;
            case "Ld":
                opcode = "001";
                break;
            case "Rtr":
                opcode = "010";
                break;
            case "Bno":
                opcode = "011";
                break;
            case "Seq":
                opcode = "100";
                break;
            case "Cnt":
                opcode = "101";
                break;
            case "Xor":
                opcode = "110";
                break;       
            case "Ls":
                opcode = "111";
                break;
            
        }

        switch (args[1]) {
            //register 1 for type r and i 
            case "A":
                arg_a = "000";
                break;
            case "r1":
                arg_a = "001";
                break;
            case "r2":
                arg_a = "010";
                break;
            case "r3":
                arg_a = "011";
                break;
            case "r4":
                arg_a = "100";
                break;
            case "r5":
                arg_a = "101";
                break;
            case "r6":
                arg_a = "110";
                break;
            case "r7":
                arg_a = "111";
                break;
            
            //immediates for type j
            default:
                arg_a = String.format("%6s", Integer.toBinaryString(jumps.get(args[1].hashCode()))).replace(' ', '0');
                /*
                if hashmap empty, just put first jump destination in.
                otherwise, check if dest is already in map, put into map if not, return existing number if yes
                */
                /*if (jumps.size() == 0) {
                    arg_a = String.format("%6s", Integer.toBinaryString(0)).replace(' ', '0');
                    jumps.put(args[1], arg_a);
                }
                else {
                    if (jumps.get(args[1]) == null) {
                        arg_a = String.format("%6s", Integer.toBinaryString(jumps.size())).replace(' ', '0');
                        jumps.put(args[1], arg_a);
                    }
                    else {
                        arg_a = jumps.get(args[1]);
                    }
                }*/

        }
    
        if (!((args[0]).equals("Bno"))) {
            switch (args[2]) {
                //register 2 for type r
                case "A":
                    arg_b = "000";
                    break;
                case "r1":
                    arg_b = "001";
                    break;
                case "r2":
                    arg_b = "010";
                    break;
                case "r3":
                    arg_b = "011";
                    break;
                case "r4":
                    arg_b = "100";
                    break;
                case "r5":
                    arg_b = "101";
                    break;
                case "r6":
                    arg_b = "110";
                    break;
                case "r7":
                    arg_b = "111";
                    break;
                  
                //immediates for type i
                default:
                    temp = Integer.parseInt(args[2]);
                    arg_b = String.format("%3s", Integer.toBinaryString(temp)).replace(' ', '0');     
            }
        }
           
        return opcode + arg_a + arg_b;
    }
    
    public static void main (String [] args) throws IOException {

        File file = new File ("raw_program2.txt");
        Scanner scan = new Scanner (file);
        Scanner jumpScanner = new Scanner(file);

        int indexOfJump = 0;
        int count = 0;
        String jumpPoints = "";
        while(jumpScanner.hasNextLine()) {
            String line = jumpScanner.nextLine();
            line = line.replace(",", "");
            line = line.replace("\t", "");
            int commentStart = line.indexOf('#');
            if(commentStart != -1)
            {
                line = line.substring(0, commentStart);
            }
            if(line.length() == 0)
            {
                continue;
            }
            if(line.indexOf(':') != -1)
            {
                jumpPoints = jumpPoints.concat(Integer.toBinaryString(count) + "\n");
                line = line.replace(":", "");
                System.out.println(line);
                jumps.put(line.hashCode(), indexOfJump);
                indexOfJump++;

                continue;
            }

            count++;
        }

        String assembly = "";
        //int linecntr = 0;

        int i = 1;
        while(scan.hasNextLine()) {
            //reads instr from text file
            String line = scan.nextLine();
            line = line.replace(",", "");
            line = line.replace("\t", "");
            int commentStart = line.indexOf('#');
            if(commentStart != -1)
            {
                line = line.substring(0, commentStart);
            }
            i++;
            if(line.length() == 0 || line.indexOf(':') != -1)
            {
                continue;
            }

            //System.out.println(linecntr++);
            String strArray[] = line.split(" ");

            //converts instr to assembly code
            assembly = assembly.concat(machCode(strArray) + "\n");
        }
        
        FileWriter writer = new FileWriter("mc_program2.txt");
        writer.write(assembly);
        writer.close();

        FileWriter branchWriter = new FileWriter("PC_LUT2.txt");
        branchWriter.write(jumpPoints);
        branchWriter.close();
    }
}
