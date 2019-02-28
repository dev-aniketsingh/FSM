package fsm;
import java.util.*;
public class FSM {

    private static int confidenceLvel = 5;
    private static int energyLevel = 5;
    private static int excitmentLevel = 0;
    private static int angerLevel = 0;


    public static abstract class State {
        protected Scanner input = new Scanner(System.in);
        protected Random random = new Random();
        public State() {}
        public abstract void enter();
        public abstract void exit();
        public abstract int updateAndCheckTransitions();
        public boolean timeOuts() {
            boolean result = false;
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter Y to Continue");
            String confirmationString = scan.nextLine();
            if(confirmationString.equals("Y")) {
                result = true;
            }
           return result;
        }
    }

    public static class chasePlayer extends State {

        public chasePlayer() {
            super();
        }
        @Override
        public void enter() {
            System.out.println("The gorilla is chasing the player");
        }

        @Override
        public void exit() {
            System.out.println("The gorilla stopped chasing player");
        }

        @Override
        public int updateAndCheckTransitions() {
            int playerCharacterDistance;
            System.out.println("is The Player still nearby? (1 or 0) ");
            playerCharacterDistance = input.nextInt();
            int num = 0;
            if(playerCharacterDistance > 10) {
                System.out.println("The player is bit far away, gorilla stopped chasing");
                if(timeOuts()) {
                    num = 5;  /**Gorilla will go to eat somewhere*/
                    energyLevel -= 1;
                }
                else {
                    num = 1;
                }
            }
            else if(playerCharacterDistance >  5 && playerCharacterDistance <= 10) {
                System.out.println("The gorilla is chasing player");
                num = 4;    /**Gorilla will walk around */
                energyLevel -= 1;
                confidenceLvel += 2;
                angerLevel += 1;
            }
            else if(playerCharacterDistance >=1 && playerCharacterDistance <= 5) {
                System.out.println("The gorilla is nearby player character and will start to hit player");
                System.out.println("The player is visible");
                angerLevel += 3;
                energyLevel -= 1;
                num = 3;      /**go to hit player*/
            }
            return num;
        }
    }

     public static class makingFriend extends State {
         public makingFriend() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("Gorilla is making friend");
        }

        @Override
        public void exit() {
            System.out.println("Gorilla made a friend");
        }

        @Override
        public int updateAndCheckTransitions() {
            int num = 0;
            System.out.println("Gorilla made a friend, gorilla will run with his friend");
            confidenceLvel++; //made friend so more confident
            if(confidenceLvel > 5) {
                System.out.println("Gorilla will run with friend");
                num = 0;
            }
            else {
                System.out.println("Gorilla is not very confident, so gorilla will walk");
                num = 4;

            }
             return num;
        }
     }
     public static class defendingPlayer extends State {
         public defendingPlayer() {
             super();
         }

         @Override
         public void enter() {
             System.out.println("Gorilla is defending");
         }

         @Override
         public void exit() {
             System.out.println("Gorilla stopped defending");
         }

         @Override
         public int updateAndCheckTransitions() {
             int num = 0;
             System.out.println("Is Gorilla  angry");
             angerLevel = random.nextInt(10);
             if(angerLevel > 5) {
                 System.out.println("Gorilla is mad at player");
                 if(timeOuts()) {
                     num = 3;
                 }
                 else {
                     num = 7;
                 }
             }
             else if(angerLevel > 3) {
                 System.out.println("Gorilla isn't very mad, so gorilla will chase");
                 if(timeOuts()) {
                     num = 1;
                 }
                 else {
                     num = 7;
                 }
             }
             else {
                 System.out.println("Gorilla is gonna run away");
                 if(timeOuts()) {
                     num = 0;
                 }
                 else {
                     num = 7;
                 }
             }
             return num;
         }
     }

     public static  class hitPlayer extends State {
        public hitPlayer() {
            super();
        }

        @Override
        public void enter() {
            System.out.println("The gorilla is hitting the player");
        }

        @Override
        public void exit() {
            System.out.println("The gorilla stopped hitting player now");
        }

        @Override
        public int updateAndCheckTransitions() {
            int playerDistance;
            System.out.println("Is player still nearby? Distance of player 1-5");
            playerDistance = input.nextInt();
            int num = 0;
            if(playerDistance > 5) {
                num = 7;
                System.out.println("The player is not in range to get hit");
                energyLevel -= 1;
                confidenceLvel += 1;
                angerLevel += 1;
            }
            else if(playerDistance >=1 && playerDistance < 5) {
                System.out.println("Gorilla is defending the player character");
                num = 7;
                angerLevel += 5;
                confidenceLvel += 3;
                energyLevel -= 1;
            }
            else {
                num = 1;
            }
            return num;
        }
    }
    public static class gorillaWalking extends State {
         public gorillaWalking() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("Gorilla is walking");
        }

        @Override
        public void exit() {
            System.out.println("Gorilla stopped walking");
        }

        @Override
        public int updateAndCheckTransitions() {
            int num = 0;
            System.out.println("Did gorilla see something? Y or N");
            String s = input.nextLine();
            System.out.println("What did gorilla See?");
            System.out.println("Options:");
            System.out.println("1. Player Character");
            System.out.println("2. Food");
            System.out.println("3. Another Gorilla");
            int randomNum = random.nextInt(3);
            if(randomNum == 1) {
                System.out.println("Gorilla say player character and will mock player now");
                num = 2;
            }
            else if(randomNum == 2) {
                System.out.println("Gorilla saw food and will eat food now");
                num = 5;
            }
            else {
                System.out.println("Gorilla will make friend");
                num = 8;
            }
             return num;
        }
    }
    public static class gorillaJumping extends State {
         public gorillaJumping() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("Gorilla is jumping");
        }

        @Override
        public void exit() {
            System.out.println("Gorilla stopped jumping");
        }

        @Override
        public int updateAndCheckTransitions() {
            int num = 0;
            System.out.println("How excited  gorilla is?");
            System.out.println(excitmentLevel);
            if(excitmentLevel > 5 && excitmentLevel <= 10)  {
                System.out.println("Gorilla is still jumping");
                num = 5;
            }
            else if(excitmentLevel < 5 && excitmentLevel > 0) {
                System.out.println("Gorilla almost stopped jumping");
                num = 4;
            }
            else {
                System.out.println("Gorilla seems to be tired");
                num = 8;
            }
             return num;
        }
    }

    public static class gorillaEating extends State {
         public gorillaEating() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("Gorilla is eating");
        }

        @Override
        public void exit() {
            System.out.println("Gorilla Stopped Eating");
        }

        @Override
        public int updateAndCheckTransitions() {
             int num = 0;
             String isEating;
             System.out.println("Is gorilla eating? Y or N");
            Scanner scan = new Scanner(System.in);
            isEating = scan.nextLine();
            if(isEating.equals("Y")) {
                energyLevel += 6;
                excitmentLevel += 4;
                System.out.println("Gorilla ate some food and gorilla will sleep now");
                num = 6;        /***Gorilla will jump*/
            }
            else if (isEating.equals("N")) {
                num = 4;    /***Gorilla is not eating anymore gorilla will walk*/
            }
            else {
                System.out.println("Enter again, is the gorilla eating");
                updateAndCheckTransitions();
            }
            return num;
        }
    }

    public static class Mocking extends State {
         public Mocking() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("The Gorilla is mocking the player");
        }

        @Override
        public void exit() {
            System.out.println("The gorilla has stopped mocking player");
        }

        @Override
        public int updateAndCheckTransitions() {
            int playerDistance;
            System.out.println("Is the player nearby");
            playerDistance = random.nextInt(10) + 1;
            System.out.println("Gorilla is " + playerDistance+ " ft of the player");
            int num = 0;
            if(playerDistance <= 10 && playerDistance >= 5) {
                System.out.println("The gorilla is mocking player");
                if(timeOuts()) {
                    num = 1;
                    angerLevel += 2;
                }
                else {
                    num = 2;
                }
            }
            else if(playerDistance < 5 && playerDistance > 1) {
                if(timeOuts()) {
                    num = 3;        /***Gorilla will hit the player*/
                    angerLevel += 4;
                }
                else {
                    num = 2;
                }
            }
            else {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter Y to Continue");
                String s = scan.nextLine();
                if(s.equals("Y")) {
                    num = 3;
                    angerLevel += 5;
                }
            }
             return num;
        }
    }
    public static class Sleep extends State {
         public Sleep() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("The gorilla is sleeping");
        }

        @Override
        public void exit() {
            System.out.println("The gorilla just woke up");
        }

        @Override
        public int updateAndCheckTransitions() {
            int num = 0;
            System.out.println("Is the gorilla sleeping? Y or N");
            String s;
            s = input.nextLine();
            if(s.equals("Y")) {
                    num = 6; /**Go to Walking*/
                    energyLevel += 5;
                    confidenceLvel += 1;
                    angerLevel -= 3;

            }
            else {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter Y to Continue");
                String sT = scan.nextLine();
                if(sT.equals("Y")) {
                    num = 4;
                }
            }
             return num;
        }
    }
    public static class Running extends State {
         public Running() {
             super();
         }

        @Override
        public void enter() {
            System.out.println("The gorilla is Running");
        }

        @Override
        public void exit() {
            System.out.println("The Gorilla is not Running");
        }

        @Override
        public int updateAndCheckTransitions() {
            String playerIsVisible;
            System.out.println("Is the player visible? Y or N");
            playerIsVisible = input.nextLine();
            int num;
            if(playerIsVisible.equals("Y")) {
                if(timeOuts()) {
                    num = 1;
                }
                else {
                    num = 0;
                }
            }
            else {
                if(timeOuts()) {
                    num = 4;
                }
                else {
                    num = 0;
                }
            }
            return num;
        }
    }
    /**
     * Main driver for FSM
     */

    public static void main(String[] args) {
        int numberOfStates = 9;
        State[] states = new State[numberOfStates];
        states[0] = new Running();
        states[1] = new chasePlayer();
        states[2] = new Mocking();
        states[3] = new hitPlayer();
        states[4] = new gorillaWalking();
        states[5] = new gorillaEating();
        states[6] = new gorillaJumping();
        states[7] = new defendingPlayer();
        states[8] = new makingFriend();


        /**** End of code to modify ****/

        int currentState = 0;
        int nextState;

        states[0].enter();
        while(true) {
            nextState = states[currentState].updateAndCheckTransitions();
            if (nextState != currentState) {
                states[currentState].exit();
                currentState = nextState;
                states[currentState].enter();
            }
        }
    }
}

