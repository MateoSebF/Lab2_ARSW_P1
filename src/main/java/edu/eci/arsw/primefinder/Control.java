/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 40000000;
    private final static int TMILISECONDS = 3000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    private long initialMS;

    private Control() {
        
        super();
        initialMS = System.nanoTime(); 
        this.pft = new PrimeFinderThread[NTHREADS];

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }
        try {
            boolean isAllDead = false;
            while(!isAllDead){
                isAllDead = true;
                for (int i = 0; i < NTHREADS; i++) {
                    if(pft[i].isAlive()){
                        isAllDead=false;
                        if(pft[i].isPaused())pft[i].resumeThread();
                    }
                }
                if(isAllDead)break;
                Thread.sleep(TMILISECONDS);
                int totalPrimes = 0;
                for (int i = 0; i < NTHREADS; i++) {
                    pft[i].pauseThread();
                    totalPrimes += pft[i].getActualPrimes();
                }
                System.out.println("Total primes calculated: "+totalPrimes);
                System.out.println("Press enter to continue");
                scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
