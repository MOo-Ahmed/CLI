/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;
import java.lang.Math;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class MyTerminal {

    public void mv(String sourcePath, String destinationPath) {
        Path result = null;
        try {
            if (sourcePath.contains(":") == false) {
            sourcePath = System.getProperty("user.dir") + "\\" + sourcePath;
        }
             if (destinationPath.contains(":") == false) {
            destinationPath = System.getProperty("user.dir") + "\\" + destinationPath;
        }
            result = Files.move(Paths.get(sourcePath), Paths.get(destinationPath));
        } catch (IOException e) {
            System.out.println("Exception while moving file: " + e.getMessage());
        }

        if (result != null) {
            System.out.println("File moved successfully. :D");
        } else {
            System.out.println("File movement failed. :)");
        }
    }

    public void cp(String src, String dst) throws IOException {
        if (src.contains(":") == false) {
            src = System.getProperty("user.dir") + "\\" + src;
        }
             if (dst.contains(":") == false) {
            dst = System.getProperty("user.dir") + "\\" + dst;
        }
        Path sourceDirectory = Paths.get(src);
        Path targetDirectory = Paths.get(dst);
        Files.copy(sourceDirectory, targetDirectory);
    }

    public void rm(ArrayList<String> args) {
        for (int i = 0; i < args.size(); i++) {
            String dir = new String();
            if(args.get(i).contains(":") == false){
                 dir = System.getProperty("user.dir") + "\\" +args.get(i);
            }
            else dir = args.get(i);
            File file = new File(dir);

            if (file.delete()) {
                System.out.println("File deleted successfully :D");
            } else {
                System.out.println("Failed to delete the file");
            }
        }

    }

    public String pwd() {
        return ("Working Directory = " + System.getProperty("user.dir"));
    }

    public void cat(ArrayList<String> args) throws IOException {
        if (args.contains(">") || args.contains(">>") || args.size() == 1) {
            if (args.size() == 1) {
                if (args.get(0).contains(">")) {
                    createFile(args.get(0).substring(1));
                } else if (args.get(0).startsWith(">") == false) {
                    ReadAndWrite(args.get(0), false, false);
                }
            } else if (args.size() == 2) {
                if (args.get(0).equals("-s") == false && args.get(0).equals("-n") == false) {
                    ReadAndWrite(args.get(0), false, false);
                    System.out.println();
                    ReadAndWrite(args.get(1), false, false);
                } else if (args.get(0).equals("-n")) {
                    ReadAndWrite(args.get(1), false, true);
                } else if (args.get(0).equals("-s")) {
                    ReadAndWrite(args.get(1), true, false);
                }
            } else if (args.size() == 3) {
                if (args.get(1).equals(">")) {
                    File file = new File(args.get(2));
                    if (file.exists() == true) {
                        deleteDir(file);
                    }
                    cp(args.get(0), args.get(2));
                } else if (args.get(1).equals(">>")) {
                    AppendToFile(args.get(0), args.get(2));
                }
            }
        } else {
            for (int i = 0; i < args.size(); i++) {
                ReadAndWrite(args.get(i), false, false);
                System.out.println("\n");
            }
        }

    }

    public void mkdir(String dir) throws IOException {
        if (dir.contains(":") == false) {
            dir = System.getProperty("user.dir") + "\\" + dir;
        }
        Path path = Paths.get(dir);
        Files.createDirectories(path);
        System.out.println(dir + " created successfully :D");

    }

    public void cd(String dir) {
        System.getProperty("user.dir");
        System.setProperty("user.dir", dir);
    }

    public  String date() {
        Format dateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy");
        String res = dateFormat.format(new Date());
        Date date = new Date();
        Date d = new Date();
        SimpleDateFormat simpDate;
        simpDate = new SimpleDateFormat("kk:mm:ss");
        //System.out.println();
        return (simpDate.format(d).toString() + " , " + res);

    }

    public void rmdir(String dir) throws IOException {
        if (dir.contains(":") == false) {
            dir = System.getProperty("user.dir") + "\\" + dir;
        }
        File file = new File(dir);
        deleteDir(file);
    }

    public void clear() {
        for (int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
    }

    public void more(String src) throws IOException {
        String line = null;
        FileReader fileReader = new FileReader(src);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0; //counter for 10 lines
        while ((line = bufferedReader.readLine()) != null) {
            //line += "\n";
            System.out.println(line);
            i++;
            while (i == 10) {
                System.out.println("\nEnter 1 to continue : ");
                Scanner input = new Scanner(System.in);
                String[] in = input.nextLine().split("");
                if (in[0].equals("1")) {
                    i = 0;
                }
            }
        }
        bufferedReader.close();
    }

    public void args(String CommandArgument) {
        HashMap<String, String> CommandsList = new HashMap<String, String>();
        CommandsList.put("mv", "[source dir.] [destination dir.]");
        CommandsList.put("cp", "[source file dir.] [destination file dir.]");
        CommandsList.put("rm", "[file1] [file2] etc.");
        CommandsList.put("pwd", "no arguments");
        CommandsList.put("date", "no arguments");
        CommandsList.put("cd", "[new directory]");
        CommandsList.put("mkdir", "[directory1] [directory2]");
        CommandsList.put("rmidr", "[directory1] [directory2]");
        CommandsList.put("ls", "no arguments");
        CommandsList.put("cat", "[file1] [file2] etc.");
        CommandsList.put("more", "[filename]");
        CommandsList.put("args", "command word . ex : cp");
        CommandsList.put("help", " [command]");
        CommandsList.put("clear", "no arguments");
        CommandsList.put("exit", "no arguments");

        if (CommandsList.get(CommandArgument) != null) {
            System.out.println(CommandArgument + " : " + CommandsList.get(CommandArgument));
        } else {
            Iterator<Map.Entry<String, String>> itr = CommandsList.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    public void help(String CommandToKnow) {
        HashMap<String, String> Commands = new HashMap<String, String>();
        Commands.put("mv", " Moves one or more files or directories from one place to another.");
        Commands.put("cp", "Copies files or group of files or directory");
        Commands.put("rm", "Removes objects such as files, directories");
        Commands.put("pwd", "Prints the path of the working directory, starting from the root");
        Commands.put("date", "Displays or sets the date.");
        Commands.put("cd", "Displays the name of or changes the current directory.");
        Commands.put("mkdir", "Creates a directory.");
        Commands.put("rmidr", "Removes a directory.");
        Commands.put("ls", "Lists the files and folders in the directory you specify.");
        Commands.put("cat", "Lists the contents of files to the terminal window.");
        Commands.put("more", "Views the text files in the command prompt");
        Commands.put("args", "list all parameters on the command line");
        Commands.put("help", " Provides Help information Linux commands.");
        Commands.put("clear", "Clears the terminal screen");
        Commands.put("exit", "Stop all");

        if (Commands.get(CommandToKnow) != null) {
            System.out.println(CommandToKnow + " : " + Commands.get(CommandToKnow));
        } else {
            Iterator<Map.Entry<String, String>> itr = Commands.entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    public void ls(String dir) {
        File file = new File(dir);
        File[] files = file.listFiles();
        for (File f : files) {  // iterates on the files array and prints each member of that array
            System.out.println(f.getName());
        }
    }

    public void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    public String FileSize(String dir) {
        File file = new File(dir);
        double kiloBytes = 0;
        if (file.exists()) {
            if (file.length() > 1024) {
                return Math.ceil(file.length() / 1024) + "    KB";
            } else {
                return Math.ceil(file.length()) + "    Bytes";
            }
        } else {
            System.err.println("File doesn't exist !!");
        }
        return " 0 Bytes";
    }

    public String lastModified(String dir) {
        File file = new File(dir);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    public void ReadAndWrite(String fname, boolean isSuppressed, boolean putNumbers) throws FileNotFoundException, IOException {
        Scanner scan = new Scanner(System.in);
        String line = null;//$cat a.txt rr.txt  $cd E:/Temp_downloads/d1
        if (fname.contains(":") == false) {
            fname = System.getProperty("user.dir") + "\\" + fname;
        }
        FileReader fileReader = new FileReader(fname);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            i++;
            if (isSuppressed && line.equals("")) {
                continue;
            }
            if (putNumbers) {
                System.out.println(i + ". " + line);
            } else {
                System.out.println(line);
            }
        }
        bufferedReader.close();
    }

    public void createFile(String fname) throws IOException {
        FileOutputStream out = new FileOutputStream(fname);
    }

    public void AppendToFile(String src, String dst) throws IOException {
        Scanner scan = new Scanner(System.in);
        String line = null;

        FileReader fileReader = new FileReader(src);
        Files.write(Paths.get(dst), ("\n").getBytes(), StandardOpenOption.APPEND);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            line += "\n";
            Files.write(Paths.get(dst), line.getBytes(), StandardOpenOption.APPEND);
        }

        bufferedReader.close();
    }

}
