import java.util.Random;

public class Filosofo extends Thread{
	private int id;
	private int qtoComeu;
	
	Filosofo(int id){
		this.id = id;
		qtoComeu = 0;
	}
	
	public void run(){
		while(Ep3.R > 0){
			pensa();
			tentaComer();
			come();
		 	devolveGarfos();
		}
		
		Ep3.fimDasContas[id] = qtoComeu;
	}
	
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
	
	public void come() {
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
	
	public void pensa() {
	 	Random ger = new Random();
	 	long tempoPensando = ger.nextInt(4000);  // tempo maximo eh 4000 ms
	 	 
	 	tempoPensando += 1000;		// tempo minimo eh 1000 ms 
	 
	 	try {
	 		sleep(tempoPensando);
	 	} catch (InterruptedException e) {
	 		e.printStackTrace();
	 	}
	 }
	
	public void imprimeInfoInicio(){
		// IMPRIME ID DO FILOSOFO + INSTANTE QUE COMEÇAM AS GARFADAS (INICIO)
		Ep3.monitor.imprimeMensagem("O filosofo "+id+" comecou a comer. "+ Ep3.tempoCorrente());		 	 
	}
	
	public void imprimeInfoFim(){
		// IMPRIME ID DO FILOSOFO + INSTANTE QUE TERMINAM AS GARFADAS (FIM)
	 	Ep3.monitor.imprimeMensagem("O filosofo "+id+" terminou de comer. " + Ep3.tempoCorrente());
	}
	
	public int garfoEsquerdo(int pos){
		return pos;
	}

	public int garfoDireito(int pos){
		return (pos+1)%Ep3.N;
	}
}

