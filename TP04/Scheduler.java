// Credits
// Student: Louis Ian Silva dos Santos - 402525
// CSV generator: https://www.mockaroo.com

package TP04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public final class Scheduler{
    // Global simulated cpu clock
    private static int clock = 0;

    // List of processes
    private static ArrayList<int[]> procList = new ArrayList<>();
    private static ArrayList<int[]> waitingQueue = new ArrayList<>();
    private static int[] executingProcess = {0, 0, 0, 0};
    private static int remainingTime = executingProcess[2];

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

            br.readLine(); // Just get rid of the CSV header
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

    public static void bumpTurnaroundTimers(){
        if(turnaroundList.size() > 0){
            turnaroundList.sort(Comparator.comparingInt(o -> o[0])); // Reorders processes by ID, just in case a process with low ID has been added

            for (int[] ints : waitingQueue) {
                for (int[] value : turnaroundList) {
                    if (value[0] == ints[1]) {  // If IDs match, bump timers
                        value[1]++;
                    }
                }
            }
        }
    }

    public static void fcfs(){

        procList.sort(Comparator.comparingInt(o -> o[0])); // Orders processes by arrival

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

        procList.sort(Comparator.comparingInt(o -> o[0])); // Orders processes by arrival

        while(remainingTime > 0 || waitingQueue.size() > 0 || procList.size() > 0){

            while (procList.size() > 0 && procList.get(0)[0] == clock) {
                waitingQueue.add(procList.get(0));
                waitingQueue.sort(Comparator.comparingInt(o -> o[2])); // Orders processes by remaining-time
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
    
    public static void sjfP(){

        procList.sort(Comparator.comparingInt(o -> o[0])); // Orders processes by arrival

        while(remainingTime > 0 || waitingQueue.size() > 0 || procList.size() > 0){

            while (procList.size() > 0 && procList.get(0)[0] == clock) {
                waitingQueue.add(procList.get(0));
                waitingQueue.sort(Comparator.comparingInt(o -> o[2])); // Orders processes by remaining-time
                int[] pairAux = {procList.get(0)[1], 0};
                turnaroundList.add(pairAux);
                procList.remove(0);
                finishedProcesses++;
            }

            if(remainingTime > 0) {
                totalProcessingTime++;

                if(waitingQueue.get(0)[2] < remainingTime){
                    executingProcess[2] = remainingTime;
                    waitingQueue.add(executingProcess);
                    waitingQueue.sort(Comparator.comparingInt(o -> o[3])); // Orders processes by priority
                    executingProcess = waitingQueue.get(0);
                    remainingTime = executingProcess[2] - 1;
                } else {
                    remainingTime--;
                }

                bumpTurnaroundTimers();
            }

            clock++;
            totalRunningTime++;
        }
    }
    
    public static void priority(){

        procList.sort(Comparator.comparingInt(o -> o[0])); // Orders processes by arrival

        while(remainingTime > 0 || waitingQueue.size() > 0 || procList.size() > 0){

            while (procList.size() > 0 && procList.get(0)[0] == clock) {
                waitingQueue.add(procList.get(0));
                waitingQueue.sort((o1, o2) -> o2[3] - o1[3]); // Reorders processes by priority
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
        for (int[] ints : turnaroundList) {
            totalTurnaround += ints[1];
        }

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
                case "SJF":
                    sjf();
                    generateStatistics();
                    printStatistics("Shortest Job First", "none");
                    break;
                case "SJFP":
                    sjfP();
                    generateStatistics();
                    printStatistics("Shortest Job First - Preemptive", "none");
                    break;
                case "PRIORITY":
                    priority();
                    generateStatistics();
                    printStatistics("Highest priority first", "none");
                    break;
                case "PRIORITYP":
                    priorityP();
                    generateStatistics();
                    printStatistics("Highest priority first - Preemptive", "none");
                    break;
                case "RR":
                    rr(Integer.parseInt(args[1]));
                    generateStatistics();
                    String q = args[2].split("=")[1];
                    printStatistics("Round-Robin", "quantum: " + q);
                    break;
                default:
                    System.out.println("Could not recognize the requested algorithm.\nPresent algorithms:\n");
                    System.out.println("FCFS\nSJF\nSJFP\nPRIORITY\nPRIORITYP\nRR quantum=<value>");
                    break;
            }
        }
    }
}