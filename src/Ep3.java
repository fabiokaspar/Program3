public class Ep3 {
	static int r;
	static char criterio;
	
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
		// exit(-1);
		}
		else{
			// TODO ler arquivo
//			arq = fopen(argv[1], "r");
			r = Integer.parseInt(args[2]);
			criterio = args[3].charAt(0);
			
//			System.out.println("File = ");
			System.out.println("r = ");
			System.out.println("Criterio = ");		
		}
	}
}
