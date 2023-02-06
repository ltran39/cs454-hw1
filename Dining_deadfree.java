public class Dining_deadfree {
    // 5 chopstick
    public boolean [] chopsticks= {false,false,false,false,false};
    Thread [] philosophers = null;

    public void eatingPhilosophers(){
        // Create 5 customers 
        philosophers = new Thread[5];

        // 5 Customers start eating
        for (int i = 0; i < 5; i++ ){
            int index = i;
            philosophers[i] = new Thread(()->{

                // Geting the chop stick
                synchronized(chopsticks) {
                    while (chopsticks[index] == true || chopsticks[(index+1) % 5] == true ){
                        try{
                            // if the chopstick is being used, Chill and wait to it~
                            chopsticks.wait();
                        } catch (InterruptedException error)  {
                            throw new RuntimeException(error);
                        }
                    }
                    // using the left chop stick
                    chopsticks[index] = true;
                    // using right chopstick
                    chopsticks[(index+1) % 5] = true;
                }
                
                // Having the chopstick, Itadakimasu !!
                System.out.println("Philosopher " +index+" is having meal now");

                // Put down the chopstick and anouce
                synchronized(chopsticks) {
                    // puting down the left chop stick
                    chopsticks[index] = false;
                    // putiting down right chopstick
                    chopsticks[(index+1) % 5] = false;
                    // Annouce "hey, y'all can use chopstick now"
                    chopsticks.notifyAll();;
                }
            });
            philosophers[i].start();
        }

        // Join them
        for (int i = 0; i < 5; i++){
            try {
                philosophers[i].join();
            } catch (InterruptedException error) {
                throw new RuntimeException(error);
            } 
        }
    }
}