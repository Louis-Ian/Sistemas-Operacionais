package TP04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Scheduler{
    private static ArrayList<String[]> pList = new ArrayList<>();

    public static void stackProcesses(String fileName) throws IOException{
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            
            String line;
            String[] process;
            
            while((line = br.readLine()) != null){
                process = line.split(", ");
                pList.add(process);
            }

            br.close();
        } catch(FileNotFoundException notf){
            System.out.println("File not found.");
        }
    }

    public static void fcfs(){
        
    }
    
    public static void sjf(){
        
    }
    
    public static void sjfP(){
        
    }
    
    public static void priority(){
        
    }
    
    public static void priorityP(){
        
    }
    
    public static void rr(int quantum){
        
    }

    public static void printHeader(String algorithm, String parameter){

    }
    
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Not enough parameters.");
            System.out.println("To execute, type: java Scheduler <path to processes file> <scheduling algorithm> [<quantum>=<value>]\n");
        } else {
            try{
                stackProcesses(args[0]);
            } catch(IOException iox){
                //TO DO: treat exception
            }

            switch (args[1]) {
                case "FCFS":

                    break;
            
                default:
                    break;
            }
        }
    }
}