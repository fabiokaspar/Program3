import java.util.Random;


public class Filosofo extends Thread{
	private int id;
	private int qtoComeu;
	
	Filosofo(int id){
		this.id = id;
		qtoComeu = 0;
	}
	
	public void run(){
		while(Ep3.R != 0){
			pensa();
			
			tentaComer();
			
			imprimeInfoInicio();
			
			come();
			
			imprimeInfoFim();
			
		 	devolveGarfos();
		}
		
		Ep3.fimDasContas.add(id, qtoComeu);
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
	 		sleep(1000);
	 		Ep3.monitor.comePorcao();
	 	} catch (InterruptedException e) {
	 		e.printStackTrace();
	 	}
	 	
	 	qtoComeu++;
	 }
	
	public void pensa() {
	 	Random ger = new Random();
	 	long tempoPensando = ger.nextInt(4000);
	 	 
	 	tempoPensando += 1000;		// tempo minimo eh 1000
	 
	 	try {
	 		sleep(tempoPensando);
	 	} catch (InterruptedException e) {
	 		e.printStackTrace();
	 	}
	 }
	
	public void imprimeInfoInicio(){
		// IMPRIME ID DO FILOSOFO (INICIO)
	 	System.out.print("O filosofo "+Integer.toString(id)+" comecou a comer ");		 	 
	 	
	 	// IMPRIME INSTANTE QUE COMEÇAM AS GARFADAS
	 	Ep3.imprimeInstanteTempo();
	}
	
	public void imprimeInfoFim(){
		// IMPRIME ID DO FILOSOFO (FIM)
	 	System.out.print("O filosofo "+Integer.toString(id)+" terminou de comer "); 
	 	
	 	// IMPRIME INSTANTE QUE TERMINAM AS GARFADAS
	 	Ep3.imprimeInstanteTempo();
	}
	
	public int garfoEsquerdo(int pos){
		return pos;
	}

	public int garfoDireito(int pos){
		return (pos+1)%5;
	}
}

