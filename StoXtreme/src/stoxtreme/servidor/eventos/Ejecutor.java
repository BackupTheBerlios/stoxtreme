package stoxtreme.servidor.eventos;

public class Ejecutor {
	private Ejecutor(){
	}

	// TODO IMPLEMENTAR LA CLASE, QUIZAS EJECUTAR DEBERIA SER ESTATICO
	public static void ejecuta(String s){
		if(s.equals("DIHOLA")){
			System.out.println("Hola!!");
		}
	}
}
