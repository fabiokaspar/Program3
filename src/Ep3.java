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
	static ArrayList<Integer> fimDasContas;
	
	public static void main(String[] args) {
		inicio = System.currentTimeMillis();
		
		parserEntrada(args);
		fimDasContas = new ArrayList<Integer>(N);
		monitor = new Monitor(N);
		
		for(int i = 0; i < N; i++){
			Runnable s = new Filosofo(i);
			new Thread(s).start();
		}
		
		for(int i = 0; i < N; i++){
			System.out.println("O filoso "+ i +" comeu "+ fimDasContas.get(i) + "porcoes.");
		}		
	}
	
	public static void imprimeInstanteTempo() {
	 	long fim = System.currentTimeMillis();
	 	System.out.println("no instante: "+ Long.toString(fim-inicio) +" ms");
	 }
	
	public static void parserEntrada(String[] args){
		if(args.length != 4){
			System.out.println("Formato esperado: ./ep3 <file> <R> <U|P>");
			System.out.println("file := arquivo texto como especificado no enunciado");
			System.out.println("R := quantidade de porcoes comidas");
			System.out.println("U|P := situacao uniforme ou com peso");
			System.exit(-1);
		}
		else{
			try { 
				arquivo = new FileReader(args[1]); 
				arq = new BufferedReader(arquivo);
				
				leituraPesos(arq);
				
				arq.close();
				
			} catch (IOException e) { 
				System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
				System.exit(-2);
			}

			R = Integer.parseInt(args[2]);
			criterio = args[3].charAt(0);
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
		}
	}

}
