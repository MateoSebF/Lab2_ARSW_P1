package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

    int a, b;

    private List<Integer> primes;
    private final Object lock = new Object();
    private boolean isPaused = false;
    public PrimeFinderThread(int a, int b) {
        super();
        this.primes = new LinkedList<>();
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {
            synchronized (lock){
                while(isPaused){
                    try{
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }

        }
    }
    public void pauseThread(){
        synchronized (lock){
            isPaused = true;
        }
    }
    public void resumeThread(){
        synchronized (lock){
            isPaused=false;
            lock.notify();
        }

    }

    boolean isPrime(int n) {
        boolean ans;
        if (n > 2) {
            ans = n % 2 != 0;
            for (int i = 3; ans && i * i <= n; i += 2) {
                ans = n % i != 0;
            }
        } else {
            ans = n == 2;
        }
        return ans;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public int getActualPrimes(){
        return primes.size();
    }
    public  boolean isPaused(){
        synchronized (lock){
            return isPaused;
        }
    }
}
