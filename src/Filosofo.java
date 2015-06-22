import java.util.Random;

public class Filosofo extends Thread{
	private int id;
	private int qtoComeu;
	private int cota;
	
	Filosofo(int id, int cotaPermitida){
		this.id = id;
		qtoComeu = 0;
		this.cota = cotaPermitida;
	}
	
	// codigo da thread
	public void run(){		
		while(Ep3.R > 0){
			pensa();
			
			// se filosofo não-cheio ou se resto 
			//tem que ser consumido após as rodadas
			if(qtoComeu < cota || Ep3.R <= Ep3.resto) 
			{
				tentaComer();
				come();
			 	devolveGarfos();
			}
		}
		
		Ep3.fimDasContas[id] = qtoComeu;
	}
	
	// tenta pegar os garfos evitando o "deadlock"
	public void tentaComer(){
		if(id == 0){
			Ep3.monitor.pegaGarfo(garfoEsquerdo(id));
			Ep3.monitor.pegaGarfo(garfoDireito(id));
		}
		else{
			Ep3.monitor.pegaGarfo(garfoDireito(id));
			Ep3.monitor.pegaGarfo(garfoEsquerdo(id));
		}
	}
	
	// libera os garfos, acordando os vizinhos se necessário
	public void devolveGarfos(){
		if(id == 0){
			Ep3.monitor.liberaGarfo(garfoEsquerdo(id));
			Ep3.monitor.liberaGarfo(garfoDireito(id));
		}
		else{
			Ep3.monitor.liberaGarfo(garfoDireito(id));
			Ep3.monitor.liberaGarfo(garfoEsquerdo(id));
		}
	}
	
	//se houver comida, come durante 1s e imprime os dados necessários
	public void come(){
	 	try {
	 	    if(Ep3.monitor.comePorcao()){			
	 	    	imprimeInfoInicio();
	 	    	qtoComeu++;
	 			sleep(1000);
	 			imprimeInfoFim();
	 		}
	 	} catch (InterruptedException e) {
	 		e.printStackTrace();
	 	}
	}
	
	// pensa em tempo aleatório
	public void pensa(){
	 	Random ger = new Random();
	 	long tempoPensando = ger.nextInt(4000);  // tempo maximo eh 4000 ms
	 	 
	 	tempoPensando += 1000;		// tempo minimo eh 1000 ms 
	 
	 	try {
	 		sleep(tempoPensando);
	 	} catch (InterruptedException e) {
	 		e.printStackTrace();
	 	}
	}
	
	
	// imprime atomicamente antes de comer
	public void imprimeInfoInicio(){
		// IMPRIME ID DO FILOSOFO + INSTANTE QUE COMEÇAM AS GARFADAS (INICIO)
		Ep3.monitor.imprimeMensagem("O filosofo "+id+" comecou a comer. "+ Ep3.tempoCorrente());		 	 
	}
	
	// idem depois de comer
	public void imprimeInfoFim(){
		// IMPRIME ID DO FILOSOFO + INSTANTE QUE TERMINAM AS GARFADAS (FIM)
	 	Ep3.monitor.imprimeMensagem("O filosofo "+id+" terminou de comer. " + Ep3.tempoCorrente());
	}
	
	// lado esquerdo do filósofo id == pos
	public int garfoEsquerdo(int pos){
		return pos;
	}

	// idem lado direito
	public int garfoDireito(int pos){
		return (pos+1)%Ep3.N;
	}
}

