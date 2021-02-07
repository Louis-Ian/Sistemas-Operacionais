package TP04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public final class Scheduler{
    // List of processes
    private static ArrayList<int[]> procList = new ArrayList<>();
    private static ArrayList<int[]> finalProcList = new ArrayList<>();

    // Statistics
    private static int totalProcessingTime = 0;
    private static int totalRunningTime = 0;
    private static float cpuUtilization = 100.0f;

    public static void stackProcesses(String fileName) throws IOException{
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            
            String line;
            String[] lineSplit;
            int[] process = {0, 0, 0, 0};
            
            while((line = br.readLine()) != null){
                lineSplit = line.split(", ");
                for(int i = 0; i < 4; i++){
                    process[i] = Integer.parseInt(lineSplit[i]);
                }
                procList.add(process.clone());
            }

            br.close();
        } catch(FileNotFoundException notf){
            System.out.println("File not found. Check the given path.");
        }
    }

    public static void fcfs(){
        int[] process = procList.get(0);
        finalProcList.add(process.clone());

        for(int i = 1; i < procList.size(); i++){
            int[] previousProcess = finalProcList.get(finalProcList.size() - 1);
            process = procList.get(i);

            if(process[0] < (previousProcess[0] + previousProcess[2])){
                process[0] = previousProcess[0] + previousProcess[2];
            }
            finalProcList.add(process.clone());
        }
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
        System.out.println("Algorithm used: " + algorithm);
        System.out.println("Parameters used: " + parameter);
    }

    // Generates statistics for the final process queue
    public static void generateStatistics(){
        for(int i = 0; i < finalProcList.size(); i++){
            totalProcessingTime += procList.get(i)[2];
        }

        totalRunningTime = finalProcList.get(finalProcList.size() - 1)[0] + finalProcList.get(finalProcList.size() - 1)[2];
        cpuUtilization = (float)totalProcessingTime/(float)totalRunningTime;
        cpuUtilization *= 100;
    }

    public static void printStatistics(String algorithm, String parameter){
        printHeader(algorithm, parameter);
        System.out.println("Total processing time: " + totalProcessingTime);
        System.out.println("CPU Utilization: " + cpuUtilization + "%");
    }
    
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.CEILING);

        if(args.length < 2){
            System.out.println("Not enough parameters.");
            System.out.println("To run, type: java Scheduler <path to processes file> <scheduling algorithm> [<quantum>=<value>]\n");
        } else {
            try{
                stackProcesses(args[0]);
            } catch(IOException iox){
                System.out.println("Something wnet wrong while reading the processes file.");
            }

            switch (args[1]) {
                case "FCFS":
                    fcfs();
                    generateStatistics();
                    printStatistics("First-Come,First-Served", "none");
                    break;
            
                default:
                    break;
            }
        }
    }
}