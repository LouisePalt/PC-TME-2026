package pc.philo;

import java.util.List;
import java.util.ArrayList;

public class TestPhilo {

	public static void main (String [] args) {
		final int NB_PHIL = 5;
		List<Thread> tPhil = new ArrayList<>();
		Fork [] tChop = new Fork[NB_PHIL];

		for(int i = 0; i < NB_PHIL; i++) {
			tChop[i] = new Fork();
		}

		for (int i = 0; i < NB_PHIL-1; i++) {
			tPhil.add(new Thread(new Philosopher(tChop[i], tChop[i+1])));
		}
		//tPhil.add(new Thread(new Philosopher(tChop[NB_PHIL-1], tChop[0]))); version deadlock
		tPhil.add(new Thread(new Philosopher(tChop[0], tChop[NB_PHIL-1])));

		for (Thread t : tPhil) {
			t.start();
		}

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		for (Thread t : tPhil) {
			t.interrupt();
		}

		for (Thread t : tPhil) {
			try {
				t.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println("Fin du programme");
	}
}