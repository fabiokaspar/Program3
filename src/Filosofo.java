public class Filosofo implements Runnable{
	private int id;
	
	Filosofo(int id){
		this.setId(id);
	}
	
	public void run() {
		// TODO ciclo de vida do filosofo
		System.out.println("Ola eu sou a thread"+Integer.toString(id));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
