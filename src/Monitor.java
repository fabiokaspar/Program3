import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	private boolean garfoEmUso[];
	private final Lock lock = new ReentrantLock();
	private Condition fork[];
	
	// inicializacao recebe numero de filosofos
	Monitor(int N){
		fork = new Condition[N];
		garfoEmUso = new boolean[N];
		
		for(int i = 0; i < N; i++){
			fork[i] = lock.newCondition();
			garfoEmUso[i] = false;
		}
	}

	// tenta pegar garfo na posição pos do filosofo
	public void pegaGarfo(int pos){
		lock.lock();
		try {
			while(garfoEmUso[pos]) wait(fork[pos]);
			garfoEmUso[pos] = true;
		} finally {
            lock.unlock();
        }
	}
	
	// libera garfo da posição pos do filosofo
	public void liberaGarfo(int pos){
		lock.lock();
		
		try {
			garfoEmUso[pos] = false;
			signal(fork[pos]);
		} finally {
            lock.unlock();
        }
	}
	
	// imprime atomicamente na saida padrao
	public void imprimeMensagem(String msg){
		lock.lock();
		
		try {
			System.out.println(msg);
		} finally {
            lock.unlock();
        }
	}
	
	// decrementa variável "global" R e retorna se quem chamou conseguiu "comer"
	public boolean comePorcao(){
		lock.lock();
		boolean comeu = false;
		
		try {
			if(Ep3.R > 0){
				Ep3.R--;
				comeu = true;
			}
		} finally {
            lock.unlock();
        }
		
		return comeu;
	}
	
	private void wait(Condition cv){
		try { cv.await(); 
		} catch (InterruptedException e) { 
		  e.printStackTrace(); 
		}
	}
	
	private void signal(Condition cv){
		cv.signal();
	} 
}
