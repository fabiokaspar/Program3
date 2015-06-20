import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Ep3 {	
	static int R;
	static char criterio;
	static FileReader arquivo;
	static BufferedReader arq;
	static int pesos[];
	static int N; 
	static long inicio;
	static Monitor monitor;
	static int fimDasContas[];
	
	public static void main(String[] args) {
		inicio = System.currentTimeMillis();
		parserEntrada(args);
		
		System.out.println("N = "+ N + "\n");
		
		if(N <= 2){
			System.out.println("Restrição: N deve ser > 2");
			System.exit(0);
		}
		
		Thread threadFilosofo[] = new Thread[N];
		
		inicializaEstruturas();
		
		// cria e inicia as threads
		for(int i = 0; i < N; i++){
			Runnable s = new Filosofo(i);
			threadFilosofo[i] = new Thread(s); 
			threadFilosofo[i].start();
		}
		
		// espera terminar todas threads
		for(int i = 0; i < N; i++){
			try {
				threadFilosofo[i].join();
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
		
		for(int i = 0; i < N; i++){
			fimDasContas[i] = 0; 
		}
	}
	
	public static String tempoCorrente() {
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
			try { 
				System.out.println("arg[0]:arquivo = "+ args[0]);
				System.out.println("arg[1]:R =  "+ args[1]);
				System.out.println("arg[2]:modo = "+ args[2]);
				
				arquivo = new FileReader(args[0]); 
				arq = new BufferedReader(arquivo);
				
				leituraPesos(arq);
				
				arq.close();
				
			} catch (IOException e) { 
				System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
				System.exit(-2);
			}

			R = Integer.parseInt(args[1]);
			criterio = args[2].charAt(0);
		}
	}

	static void leituraPesos(BufferedReader arq) throws IOException{
		N = Integer.parseInt(arq.readLine());	
		String linha;
		String[] temp = new String[10];
		pesos = new int[N];
		
		linha = arq.readLine();
		temp = linha.split(" ");
		
		for(int i = 0; i < N; i++){
			pesos[i] = Integer.parseInt(temp[i]);
			if(pesos[i] <= 1){
				System.out.println("Restrição: pesos devem ser > 1");
				System.exit(0);
			}
		}
	}

}
