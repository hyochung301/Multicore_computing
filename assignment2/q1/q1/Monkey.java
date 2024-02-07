package q1;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
/*
    this is weird. all of the tests only ever instantiate one monkey.
    so this class really represents the crossing, not the monkeys.
    I don't know why it's written that way? 
*/
public class Monkey {

   int nmonkeys = 0;
   int nmonfin = 0;
   boolean kong_wants = false;
   boolean kong_on = false;   public boolean kong() {return kong_on;}
   boolean left_on = false;   public boolean left() {return left_on;}
   boolean right_on = false;  public boolean right(){return right_on;}
   ReentrantLock rope;
   Condition no_right;
   Condition no_left;
   Condition no_kong;
   Condition not_full;
   Condition rope_empty;

   public Monkey() {
      rope  = new ReentrantLock(true);
      no_right    = rope.newCondition();
      no_left     = rope.newCondition();
      no_kong     = rope.newCondition();
      not_full    = rope.newCondition();
      rope_empty  = rope.newCondition();
   }

   private void ClimbRopeLeft() throws InterruptedException {
      rope.lock();
      while (kong_on || right_on || nmonkeys >= 3) {
         while (kong_on)      no_kong.await();
         while (right_on)      no_right.await();
         while(nmonkeys >= 3) not_full.await();
      }
      left_on = true;
      nmonkeys++;
      // System.out.println(String.format("LEFT CROSSIN, #%d", nmonkeys));
      if (nmonkeys>3) System.out.println("ERROR nmonks > 3");
      rope.unlock();
   }
   private void ClimbRopeRight() throws InterruptedException {
      rope.lock();
      while (kong_on || left_on || nmonkeys >= 3) {
         while (kong_on)      no_kong.await();
         while (left_on)      no_left.await();
         while(nmonkeys >= 3) not_full.await();
      }
      right_on = true;
      nmonkeys++;
      // System.out.println(String.format("RGHT CROSSIN, #%d", nmonkeys));
      if (nmonkeys>3) System.out.println("ERROR nmonks > 3");
      rope.unlock();
   }
   private void ClimbRopeKong() throws InterruptedException {
      kong_wants = true;
      rope.lock();
      while (nmonkeys!=0) rope_empty.await();
      if (nmonkeys!=0) System.out.println("ERROR: kong not alone");
      nmonkeys++;
      // System.out.println(String.format("KONG CROSSIN, #%d", nmonkeys));
      kong_on = true;
      rope.unlock();
   }

   // A monkey calls the method when it arrives at the river bank and
   // wants to climb the rope in the specified direction (0 or 1);
   // Kong’s direction is -1.
   // The method blocks a monkey until it is allowed to climb the rope.
   public void ClimbRope(int direction) throws InterruptedException {
      switch(direction) {
      case 0:
         ClimbRopeLeft(); break;
      case 1:
         ClimbRopeRight(); break;
      case -1:
         ClimbRopeKong(); break;
      default:
         System.out.println("How did you do this? Test passing non {0,1,-1} direction");
         break;
      }
   }

   // After crossing the river, every monkey calls this method which
   // allows other monkeys to climb the rope.
   public void LeaveRope() {
      rope.lock();
      if (nmonkeys==0) System.out.println("ERROR: leave called w 0 monkeys!");
      nmonkeys--;
      not_full.signalAll();
      if (nmonkeys==0) {
         rope_empty.signal(); 
         left_on = false; right_on = false; kong_on = false;
         no_left.signalAll(); no_right.signalAll(); no_kong.signalAll();
      }
      nmonfin++;
      rope.unlock();
      // if (nmonfin%10==0) System.out.println(nmonfin);
   }

   /**
   * Returns the number of monkeys on the rope currently for test purpose.
   *
   * @return the number of monkeys on the rope
   *
   * Positive Test Cases:
   * case 1: when normal monkey (0 and 1) is on the rope, this value should <= 3, >= 0
   * case 2: when Kong is on the rope, this value should be 1
   */
   public int getNumMonkeysOnRope() {
      return nmonkeys;
   }

}
