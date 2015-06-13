import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ep3 {
	static int r;
	static char criterio;
	static FileReader arquivo;
	static BufferedReader arq;
	static int pesos[];
	 
	public static void main(String[] args) {
		parserEntrada(args);
		
		for(int i = 0; i < 5; i++){
			Runnable s = new Filosofo(i);
			new Thread(s).start();
		}
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

			r = Integer.parseInt(args[2]);
			criterio = args[3].charAt(0);
		}
	}

	static void leituraPesos(BufferedReader arq) throws IOException{
		int N = Integer.parseInt(arq.readLine());
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