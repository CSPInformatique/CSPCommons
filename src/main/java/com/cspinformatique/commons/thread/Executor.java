package com.cspinformatique.commons.thread;

public abstract class Executor implements Runnable {
	private boolean completed;
	
	@Override
	public void run() {
		this.completed = false;
		this.execute();
		this.completed = true;
	}
	
	protected abstract void execute();

	public boolean isCompleted(){
		return this.completed;
	}
}
