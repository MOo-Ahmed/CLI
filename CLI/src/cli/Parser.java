package cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
//import java.lang.*;

public class Parser {

    ArrayList<String> PossibleCmds = new ArrayList();
    ArrayList<String> args = new ArrayList();// Will be filled by arguments extracted by parse method
    String cmd = new String();

    boolean isTherePipeOperator = false;
    String cmd2 = new String(); // second cmd for the pipe operator
    ArrayList<String> args2 = new ArrayList();// args for the second cmd after the pipe operator

    public Parser() {
        prepare();
    }

    public static ArrayList<String> mySplit(String str) {
        ArrayList<String> list = new ArrayList()  ;
        if(str.contains(" ") == false){
            list.add(str);
            return list ;
        }
        for(int i = 0 ; i < str.length(); i++){
            String start = new String();
            start = str.substring(i, i+1) ;
            if(start == "|"){
                list.add(start);
                System.out.println("GOT HERE\n");
                i++ ;
                continue ;
            }
            else if(start.equals("\"") == true){
                String part =new String(str.substring(i+1, str.indexOf("\"",i+1))) ;
                list.add(part) ;
                i += part.length() +3  ;   // to skip " and spaces
            }
            else{
                String part =new String(str.substring(i, str.indexOf(" ", i))) ;
                list.add(part) ;
                i += part.length()   ;
            }
            
        }
        return list ;
    }

    void prepare() {

        PossibleCmds.add("$mv");//PossibleCmds.add("fn");
        PossibleCmds.add("$cp");
        PossibleCmds.add("$rm");
        PossibleCmds.add("$pwd");
        PossibleCmds.add("$date");
        PossibleCmds.add("$cd");
        PossibleCmds.add("$mkdir");
        PossibleCmds.add("$rmdir");

        //__________________________
        PossibleCmds.add("$ls");
        PossibleCmds.add("$cat");
        PossibleCmds.add("$more");
        PossibleCmds.add("$args");
        PossibleCmds.add("$help");
        PossibleCmds.add("$clear");

    }

    boolean isCorrectCMD(String com) {

        for (int i = 0; i < PossibleCmds.size(); i++) {
            if (PossibleCmds.get(i).equals(com)) {
                return true;
            }
        }
        return false;
    }

    boolean Check(boolean b) {
        if (!b) {
            System.err.println("Incorrect command !!");
            return false;
        }
        return true;
    }

    public boolean parse(String input) {
        String inputParts[];
       
        inputParts = input.split(" ");
        List<String> list = Arrays.asList(inputParts);

        //System.out.println(list);

        if (list.contains("|")) {
            if (isCorrectCMD(list.get(0))) {
                for (int i = 1; i < (list.indexOf("|")); i++) {
                    args.add(list.get(i));
                }
                if (isCorrectCMD(list.get(list.indexOf("|") + 1))) { // this if to fill the second cmd and its args
                    for (int i = list.indexOf("|") + 2; i < list.size(); i++) {
                        args2.add(list.get(i));
                    }
                    cmd = list.get(0);
                    cmd2 = list.get(list.indexOf("|") + 1);
                    isTherePipeOperator = true;
                    return true;
                }
            }
        }
        //System.out.println(list);
        if (isCorrectCMD(list.get(0))) {
            cmd = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                args.add(list.get(i));
            }
            return true;
        } else {
            Check(false);
            return false;
        }
    }

    public String getCmd() {
        return cmd;
    }

    public ArrayList<String> getArguments() {
        return args;
    }
}
