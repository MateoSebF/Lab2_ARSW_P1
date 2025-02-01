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
    private final static int MAXVALUE = 4000;
    private final static int TMILISECONDS = 10;

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
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }
        try {
            boolean notFinished = true;
            while (notFinished) {
                long actualSEC = (System.nanoTime()-initialMS)/1000000;
                if (actualSEC % 1000 == 0){
                    System.out.println("Time:" + actualSEC);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
