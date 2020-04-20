/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

//import java.io.File;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Scanner;
//import java.util.ArrayList;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Arrays;
//import java.util.HashMap;

public class CLI {

    public static void main(String[] argums) throws IOException {

        while (true) {
            try {
                Parser p = new Parser();
                MyTerminal t = new MyTerminal();
                String Command;                                                 // these 4 lines to just input a string in Java :D
                Scanner input = new Scanner(System.in);
                System.out.println("Enter your command : ");
                Command = input.nextLine();                             // until here
                //__________________________________________________________________________
                boolean isCorrectCommand = p.parse(Command);
                if (isCorrectCommand == true) {
                    String WhatToDo = new String();
                    WhatToDo = p.getCmd();
                    ArrayList<String> args = p.getArguments();
                    //rmdir E:/Temp_downloads/photos/julian-howard-2YGrbLlbz6Y-unsplash.jpg
                    //$cat E:/Temp_downloads/flash.txt >> E:/Temp_downloads/Programs/zoom.txt
                    //$cd E:/Temp_downloads/d1
                    if (WhatToDo.equals("$mv")) {
                        if (args.size() <= 1) {
                            System.err.println("Expected more args. ");
                            continue;
                        }
                        t.mv(args.get(0), args.get(1));  // move
                    } else if (WhatToDo.equals("$cp")) {
                        if (args.size() <= 1) {
                            System.err.println("Expected more args. ");
                            continue;
                        }
                        t.cp(args.get(0), args.get(1));   // copy
                    } else if (WhatToDo.equals("$rm")) {
                        if (args.size() < 1) {
                            System.err.println("Expected more args. ");
                            continue;
                        }
                        t.rm(args);                        // delete
                    } else if (WhatToDo.equals("$pwd")) {
                        // outputs the name of the current directory
                        if (args.size() == 0) {
                            System.out.println(t.pwd());
                        } else if (args.get(0).equals(">") || args.get(0).equals(">>")) {
                            String path = args.get(1);
                            if (path.contains(":") == false) {
                                path = System.getProperty("user.dir") + "\\" + path;
                            }
                            File file = new File(path);
                            if (args.get(0).equals(">")) {
                                if (file.exists() == true) {
                                    t.deleteDir(file);
                                }
                                t.createFile(path);
                            }
                            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                            writer.append(' ');
                            writer.append(t.pwd());

                            writer.close();

                        }
                    } else if (WhatToDo.equals("$cd")) {
                        if (args.size() < 1 || args.size() > 1) {
                            System.err.println("Expected just 1 argument. ");
                            continue;
                        }
                        t.cd(args.get(0));                        // go to another directory
                    } else if (WhatToDo.equals("$mkdir")) {
                        if (args.size() < 1 || args.size() > 1) {
                            System.err.println("Expected just 1 argument. ");
                            continue;
                        }
                        for (int i = 0; i < args.size(); i++) {
                            t.mkdir(args.get(i));                 // create directories
                        }
                    } else if (WhatToDo.equals("$date")) {
                        if (args.size() == 0) {
                            System.out.println(t.date());
                        } else if (args.get(0).equals(">") || args.get(0).equals(">>")) {
                            String path = args.get(1);
                            if (path.contains(":") == false) {
                                path = System.getProperty("user.dir") + "\\" + path;
                            }
                            File file = new File(path);
                            if (args.get(0).equals(">")) {
                                if (file.exists() == true) {
                                    t.deleteDir(file);
                                }
                                t.createFile(path);
                            }
                            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                            writer.append(' ');
                            writer.append(t.date());

                            writer.close();

                        }
                    } else if (WhatToDo.equals("$rmdir")) {
                        if (args.size() < 1 || args.size() > 1) {
                            System.err.println("Expected just 1 argument. ");
                            continue;
                        }
                        t.rmdir(args.get(0));
                    } else if (WhatToDo.equals("$cat")) {
                        if (args.size() < 1) {
                            System.err.println("Expected more arguments. ");
                            continue;
                        }
                        t.cat(args);
                    } else if (WhatToDo.equals("$clear")) {
                        if (args.size() > 0) {
                            System.err.println("Expected no arguments. ");
                            continue;
                        }
                        t.clear();
                    } else if (WhatToDo.equals("$more")) {
                        t.more(args.get(0));
                    } else if (WhatToDo.equals("$args")) {
                        if (args.size() > 1 || args.size() < 1) {
                            System.err.println("Expected 1 argument. ");
                            continue;
                        }
                        t.args(args.get(0));
                    } else if (WhatToDo.equals("$help")) {
                        if (args.size() > 1 || args.size() < 1) {
                            System.err.println("Expected 1 argument. ");
                            continue;
                        }
                        t.help(args.get(0));
                    } else if (WhatToDo.equals("$ls")) {
                        t.ls(args.get(0));
                    }
                    if (p.isTherePipeOperator == true) {
                        if (p.cmd2.equals("$mv")) {
                            if (p.args2.size() != 2) {
                                System.err.println("Expected 2 args. ");
                                continue;
                            }
                            t.mv(p.args2.get(0), p.args2.get(1));  // move
                        } else if (p.cmd2.equals("$cp")) {
                            if (p.args2.size() != 2) {
                                System.err.println("Expected 2 args. ");
                                continue;
                            }
                            t.cp(p.args2.get(0), p.args2.get(1));   // copy
                        } else if (p.cmd2.equals("$rm")) {
                             if (p.args2.size() < 1) {
                                System.err.println("Expected more args. ");
                                continue;
                            }
                             t.rm(p.args2);                        // delete
                        } else if (p.cmd2.equals("$pwd")) {
                            if (p.args2.size() == 0) {
                                System.out.println(t.pwd());
                            } else if (p.args2.get(0).equals(">") || p.args2.get(0).equals(">>")) {
                                String path = p.args2.get(1);
                                if (path.contains(":") == false) {
                                    path = System.getProperty("user.dir") + "\\" + path;
                                }
                                File file = new File(path);
                                if (p.args2.get(0).equals(">")) {
                                    if (file.exists() == true) {
                                        t.deleteDir(file);
                                    }
                                    t.createFile(path);
                                }
                                BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                                writer.append(' ');
                                writer.append(t.pwd());

                                writer.close();

                            }                                       // outputs the name of the current directory
                        } else if (p.cmd2.equals("$cd")) {
                            if (p.args2.size() != 1) {
                                System.err.println("Expected 1 argument. ");
                                continue;
                            }
                            t.cd(p.args2.get(0));                        // go to another directory
                        } else if (p.cmd2.equals("$mkdir")) {
                             if (p.args2.size() != 1) {
                                System.err.println("Expected 1 argument. ");
                                continue;
                            }
                             for (int i = 0; i < p.args2.size(); i++) {
                                t.mkdir(p.args2.get(i));                 // create directories
                            }
                        } else if (p.cmd2.equals("$date")) {
                            if (p.args2.size() == 0) {
                                System.out.println(t.date());
                            } else if (p.args2.get(0).equals(">") || p.args2.get(0).equals(">>")) {
                                String path = p.args2.get(1);
                                if (path.contains(":") == false) {
                                    path = System.getProperty("user.dir") + "\\" + path;
                                }
                                File file = new File(path);
                                if (p.args2.get(0).equals(">")) {
                                    if (file.exists() == true) {
                                        t.deleteDir(file);
                                    }
                                    t.createFile(path);
                                }
                                BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                                writer.append(' ');
                                writer.append(t.date());

                                writer.close();

                            }
                        } else if (p.cmd2.equals("$rmdir")) {
                            if (p.args2.size() != 1) {
                                System.err.println("Expected 1 argument. ");
                                continue;
                            }
                            t.rmdir(p.args2.get(0));
                        } else if (p.cmd2.equals("$cat")) {
                            t.cat(p.args2);
                        } else if (p.cmd2.equals("$clear")) {
                            if (p.args2.size() != 0) {
                                System.err.println("Expected no argument. ");
                                continue;
                            }
                            t.clear();
                        } else if (p.cmd2.equals("$more")) {
                            t.more(p.args2.get(0));
                        } else if (p.cmd2.equals("$args")) {
                            if (p.args2.size() != 1) {
                                System.err.println("Expected 1 argument. ");
                                continue;
                            }
                            t.args(p.args2.get(0));
                        } else if (p.cmd2.equals("$help")) {
                            if (p.args2.size() != 1) {
                                System.err.println("Expected 1 argument. ");
                                continue;
                            }
                            t.help(p.args2.get(0));
                        } else if (p.cmd2.equals("$ls")) {
                            t.ls(p.args2.get(0));
                        }
                        p.isTherePipeOperator = false;
                    }
                } else {
                    System.err.println("Wrong command !!!!!");
                    continue;
                }
            } catch (Exception e) {
                //System.out.println(e.toString());
            }

        }
    }
}

