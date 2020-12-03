public class sProcess {
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  int priority;
  boolean check;

  public sProcess (int cputime, int ioblocking, int cpudone, int ionext, int numblocked, int priority,  boolean check) {
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.priority = priority;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
    this.check = check;
  } 	
}
