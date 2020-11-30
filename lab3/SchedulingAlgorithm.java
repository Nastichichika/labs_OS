// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector<sProcess> processVector, Results result) {
    processVector.sort((a, b) -> {
      if(a.priority == b.priority && a.cputime == b.cputime)
        return 0;
      if(a.priority == b.priority)
        return a.cputime  - b.cputime;
      return a.priority - b.priority;
    });
    int comptime = 0;
    int currentProcess = 0;
    int previousProcess = 0;
    int completed = 0;
    int size = processVector.size();
    String resultsFile = "Summary-Processes";

    result.schedulingType = "Batch (Non-preemptive)";
    result.schedulingName = "Shortest job first";
    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      sProcess process = (sProcess) processVector.get(currentProcess);
      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
      while (comptime < runtime) {
        if (process.cpudone == process.cputime) {
          completed++;
          out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
          if (completed == size) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          currentProcess++;
          processVector = removeCompleted(processVector);

          if(processVector.isEmpty())
            break;
          if(currentProcess >= processVector.size())
            currentProcess = 0;

          process = processVector.get(currentProcess);

          if(currentProcess == previousProcess && process.cpudone >= process.cputime) {
            out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone +")");
            break;
          }
          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
        }

        if (process.ioblocking == process.ionext) {
          out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
          process.numblocked++;
          process.ionext = 0;
          previousProcess = currentProcess;

          currentProcess++;
          processVector = removeCompleted(processVector);

          if(processVector.isEmpty())
            break;
          if(currentProcess >= processVector.size())
            currentProcess = 0;

          process = (sProcess) processVector.get(currentProcess);

          if(currentProcess == previousProcess && process.cpudone >= process.cputime) {
            out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
            break;
          }

          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone +  ")");
        }
        process.cpudone++;
        if (process.ioblocking > 0) {
          process.ionext++;
        }
        comptime++;
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;

    return result;
  }
  static Vector<sProcess> removeCompleted(Vector<sProcess> processVector) {
    Vector<sProcess> vector = new Vector<>();
    for (sProcess process : processVector) {
      if (process.cputime > process.cpudone)
        vector.add(process);
    }
    return vector;
  }
}
