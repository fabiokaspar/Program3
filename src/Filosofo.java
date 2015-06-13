public class Filosofo implements Runnable{
	private int id;
	
	Filosofo(int id){
		this.setId(id);
	}
	
	public void run() {
		// TODO ciclo de vida do filosofo
		
		// ao obter o garfo imprime o id
		System.out.println("O filosofo "+Integer.toString(id)+" comecou a comer");
	
		// ****COME****

		// ao devolver o garfo imprime o id
		System.out.println("O filosofo "+Integer.toString(id)+" terminou de comer");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
