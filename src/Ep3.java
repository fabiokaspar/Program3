public class Ep3 {
	public static void main(String[] args) {
		for(int i = 0; i < 5; i++){
			Runnable s = new Filosofo(i);
			new Thread(s).start();
		}
	}
}
