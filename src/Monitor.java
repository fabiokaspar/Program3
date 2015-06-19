import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	private ArrayList<Boolean> garfoEmUso;
	private final Lock lock = new ReentrantLock();
	private ArrayList<Condition> fork;
	
	Monitor(int N){
		fork = new ArrayList<Condition>(N);
		garfoEmUso = new ArrayList<Boolean>(N);
		
		for(int i = 0; i < N; i++){
			fork.add(lock.newCondition());
			garfoEmUso.add(new Boolean(false));
		}
	}
	
	// TODO: colocar lock em todos os metodos
	public void pegaGarfo(int pos){
		lock.lock();
		
		try {
			while(garfoEmUso.get(pos)) wait(fork.get(pos));
			garfoEmUso.set(pos, true);
		} finally {
            lock.unlock();
        }
	}
	
	public void liberaGarfo(int pos){
		lock.lock();
		
		try {
			garfoEmUso.set(pos, false);
			signal(fork.get(pos));
		} finally {
            lock.unlock();
        }
	}
	
	public void comePorcao(){
		lock.lock();
		
		try {
			Ep3.R--;
		} finally {
            lock.unlock();
        }
	}
	
	private void wait(Condition cv){
		try {
			cv.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void signal(Condition cv){
		cv.signal();
	} 
}
