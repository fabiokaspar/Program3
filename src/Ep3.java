import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Ep3 {	
	static int R; 						// esse R eh variavel
	static int Rin; 					// R inicial
	static int N;						// # de filosofos
	static char criterio;				// U ou P
	static long inicio;					// tempo (em ms)
	static Monitor monitor;				// monitor das threads
	static int fimDasContas[];			// quanto cada um comeu	
	static int pesos[];					// caso P
	static int somaPesos;				// caso P
	static int resto;					// resto da comida que será disputada 
	
	public static void main(String[] args) {
		inicio = System.currentTimeMillis();
		parserEntrada(args);
		
		System.out.println("arg[0]:arquivo = "+ args[0]);
		System.out.println("arg[1]:R = "+ R);
		System.out.println("arg[2]:modo = "+ criterio); 
		System.out.println("N = "+ N + "\n");
		
		Thread threadFilosofo[] = new Thread[N];
		
		// cria e inicia as threads
		if(criterio == 'U'){
			int cotaUnif = cotaFilosofoCriterioU();
			 
			for(int id = 0; id < N; id++){
				Runnable s = new Filosofo(id, cotaUnif);
				threadFilosofo[id] = new Thread(s); 
				threadFilosofo[id].start();
			}
		}
		
		else{ // caso P
			for(int id = 0; id < N; id++){
				Runnable s = new Filosofo(id, cotaFilosofoCriterioP(id));
				threadFilosofo[id] = new Thread(s); 
				threadFilosofo[id].start();
			}
		}
		
		// espera terminar todas threads
		for(int id = 0; id < N; id++){
			try {
				threadFilosofo[id].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\nNo fim das contas...\n");
		
		for(int i = 0; i < N; i++){
			System.out.println("O filosofo "+ i +" comeu "+ fimDasContas[i] + " porções");
		}
	}
	
	public static void inicializaEstruturas(){
		fimDasContas = new int[N];
		monitor = new Monitor(N);
		
		if(criterio == 'P'){
			pesos = new int[N];
		}
	}
	
	// tempo atual em String
	public static String tempoCorrente(){
	 	long fim = System.currentTimeMillis();
	 	return Long.toString(fim-inicio) +"ms";
	 }
	
	public static void parserEntrada(String[] args){
		if(args.length < 3){
			System.out.println("Formato esperado: java Ep3 <file> <R> <U|P>");
			System.out.println("file := arquivo texto como especificado no enunciado");
			System.out.println("R := quantidade de porcoes comidas");
			System.out.println("U|P := situacao uniforme ou com peso");
			System.exit(-1);
		}
		else{
			try{
				FileReader arquivo = new FileReader(args[0]);
				R = Integer.parseInt(args[1]);
				Rin = R;
				criterio = args[2].charAt(0);
				
				if(criterio != 'U' && criterio != 'P'){
					System.out.println("Critérios permitidos: U e P somente");
					System.exit(0);
				}
				
				BufferedReader arq = new BufferedReader(arquivo);
				
				N = Integer.parseInt(arq.readLine());
				
				if(N <= 2){
					System.out.println("Restrição: N deve ser > 2");
					System.exit(0);
				}
				
				inicializaEstruturas();
					
				if(criterio == 'P'){
					leituraPesos(arq);
					resto = (Rin % somaPesos);
				}
				
				else{
					resto = (Rin % N);
				}
				
				arq.close();
				
			} catch (IOException e) {
				System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
				System.exit(-2);
			}
		}
	}
	
	// seta pesos (caso P) e seta a somatoria dos mesmos
	public static void leituraPesos(BufferedReader arq){
		String linha = null;
		String[] temp = new String[N];
		somaPesos = 0;
		
		try {
			linha = arq.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		temp = linha.split(" ");
		
		for(int i = 0; i < N; i++){
			pesos[i] = Integer.parseInt(temp[i]);
			
			if(pesos[i] <= 1){
				System.out.println("Restrição: pesos devem ser > 1");
				System.exit(0);
			}
			
			somaPesos += pesos[i];
		}	
	}

	public static int cotaFilosofoCriterioU(){
		int cota = 0;
		
		if(N > 0){
			cota = (Rin/N);
		}
		
		return cota;
	}
	
	public static int cotaFilosofoCriterioP(int id){
		int cota = 0;
		
		if(somaPesos > 0){
			cota = (pesos[id] * Rin)/somaPesos;   
		}
		
		return cota;
	}

}
