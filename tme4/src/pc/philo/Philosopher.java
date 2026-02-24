package pc.philo;

import java.lang.InterruptedException;

public class Philosopher implements Runnable {
	private Fork left;
	private Fork right;

	public Philosopher(Fork left, Fork right) {
		this.left = left;
		this.right = right;
	}

	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			boolean leftHeld = false;
			boolean rightHeld = false;

			try {
				this.think();
				left.acquire();
				leftHeld = true;
				System.out.println(Thread.currentThread().getName() + " has one fork");
				right.acquire();
				rightHeld = true;
				System.out.println(Thread.currentThread().getName() + " has two fork");
				this.eat();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				if(leftHeld) {
					left.release();
				}
				if(rightHeld) {
					right.release();
				}
			}
		}
	}

	private void eat() {
		System.out.println(Thread.currentThread().getName() + " is eating");
	}

	private void think() {
		System.out.println(Thread.currentThread().getName() + " is thinking");
	}
}
