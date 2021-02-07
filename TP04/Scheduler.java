package TP04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public final class Scheduler{
    // Global simulated cpu clock
    private static int clock = 0;

    // List of processes
    private static ArrayList<int[]> procList = new ArrayList<>();
    private static ArrayList<int[]> waitingQueue = new ArrayList<>();
    private static int[] executingProcess = {0, 0, 0, 0};
    private static int remainingTime = executingProcess[2]; // TODO: Is it the value or the reference?

    // Statistics
    private static int totalProcessingTime = 0;
    private static int totalRunningTime = 0;
    private static float cpuUtilization = 0;
    private static float averageThroughput = 0;
    private static int finishedProcesses = 0;
    private static ArrayList<int[]> turnaroundList = new ArrayList<>(); // Pair: pId, waiting time
    private static float averageTurnaround = 0;

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

            procList.sort((o1, o2) -> (o1[0] - o2[0])); // Orders processes by arrival
            br.close();
        } catch(FileNotFoundException notf){
            System.out.println("File not found. Check the given path.");
        }
    }

    public static void bumpTurnaroundTimers(){
        if(turnaroundList.size() > 0){
            turnaroundList.sort((o1, o2) -> (o1[0] - o2[0])); // Reorders processes by ID, just in case a process with low ID has been added

            for(int j = 0; j < waitingQueue.size(); j++) {
                for (int i = 0; i < turnaroundList.size(); i++) {
                    if (turnaroundList.get(i)[0] == waitingQueue.get(j)[1]) {  // If IDs match, bump timers
                        turnaroundList.get(i)[1]++;
                    }
                }
            }
        }
    }

    public static void fcfs(){

        while(remainingTime > 0 || waitingQueue.size() > 0 || procList.size() > 0){

            while (procList.size() > 0 && procList.get(0)[0] == clock) {
                waitingQueue.add(procList.get(0));
                int[] pairAux = {procList.get(0)[1], 0};
                turnaroundList.add(pairAux);
                procList.remove(0);
                finishedProcesses++;
            }

            if(remainingTime > 0) {
                totalProcessingTime++;
                remainingTime--;
                bumpTurnaroundTimers();

            } else if(waitingQueue.size() > 0){
                executingProcess = waitingQueue.get(0);
                waitingQueue.remove(0);
                remainingTime = executingProcess[2] - 1;
                totalProcessingTime++;
                bumpTurnaroundTimers();
            }

            clock++;
            totalRunningTime++;
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

        cpuUtilization = ((float)totalProcessingTime/(float)totalRunningTime)  * 100;
        averageThroughput = (float)totalRunningTime/finishedProcesses;

        int totalTurnaround = 0;
        for(int i = 0; i < turnaroundList.size(); i++){ totalTurnaround += turnaroundList.get(i)[1];}

        averageTurnaround = (float)totalTurnaround/(float)finishedProcesses;
    }

    public static void printStatistics(String algorithm, String parameter){
        printHeader(algorithm, parameter);
        System.out.println("\nTotal processing time: " + totalProcessingTime);
        System.out.println("CPU Utilization: " + cpuUtilization + "%");
        System.out.println("\nAverage throughput: " + averageThroughput + " clocks/process");
        System.out.println("Average turnaround: " + averageTurnaround);
        System.out.println("Average response time: *to be honest, i don't understand how to measure it in this assignment, given the csv file only has the burst time*");
        System.out.println("Average waiting time: *same thing as the above.. should i emulate IO operations?*");
        System.out.println("Average context switching: NA");
        System.out.println("Total executed processes: " + finishedProcesses);
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
                System.out.println("Something went wrong while reading the processes file.");
            }

            switch (args[1]) {
                case "FCFS":
                    fcfs();
                    generateStatistics();
                    printStatistics("First-Come,First-Served", "none");
                    break;
            
                default:
                    System.out.println("Could not recognize the requested algorithm."); // Todo: show help with available algorithms
                    break;
            }
        }
    }
}