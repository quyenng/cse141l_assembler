import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.HashMap;


public class readFile {
    public static HashMap<String, String> jumps = new HashMap<String, String>();

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
                /* 
                if hashmap empty, just put first jump destination in. 
                otherwise, check if dest is already in map, put into map if not, return existing number if yes
                */ 
                if (jumps.size() == 0) {
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
                }

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

        File file = new File ("/Users/19qng/cse141l/assembler/ab_program1.txt");
        Scanner scan = new Scanner (file);

        String assembly = "";

        while(scan.hasNextLine()) {
            //reads instr from text file
            String line = scan.nextLine();
            String strArray[] = line.split(" ");

            //converts instr to assembly code
            assembly = assembly.concat(machCode(strArray) + "\n");
        }
        
        FileWriter writer = new FileWriter("/Users/19qng/cse141l/assembler/mc_program1.txt");
        writer.write(assembly);
        writer.close();


    }
}
