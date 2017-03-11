import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;


public class Procesador extends Thread {
	private Socket socketServicio;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	public Procesador(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	public void run(){
		procesa();
	}

	void procesa(){
			
		String datosRecibidos;
		String datosEnviar = null;
		
		try {
                    
			// Obtiene los flujos de escritura/lectura
			inputStream=socketServicio.getInputStream();
			outputStream=socketServicio.getOutputStream();
			
			//datosRecibidos se recibe desde BufferedReader
			BufferedReader inReader = new BufferedReader (new InputStreamReader(inputStream));
                        
            datosRecibidos = inReader.readLine();
            //Comprobar aqui el valor que recibe para llamar a una funcion o a otra.
            //Tambien conservar respuesta para enviar los valores que se reciben
            String[] parts = datosRecibidos.split(",");
            int opcionMenu = Integer.parseInt(parts[0]);
            System.out.println("RECIBIDO: "+datosRecibidos);
            System.out.println("OPCION: "+opcionMenu);
            String respuesta = "";
            int edad = -1;
            int pulsaciones = -1;
            int pasos = -1;
            double altura = -1;
            double peso = -1;
          	switch (opcionMenu){
          		case 1: 
          			edad = Integer.parseInt(parts[1]);
          			pulsaciones = Integer.parseInt(parts[2]);
          			respuesta=Pulsometro(edad, pulsaciones);
          			break;
          		case 2: 
          			pasos = Integer.parseInt(parts[1]);
          			//altura = Integer.parseInt(parts[2]);
                                altura = Double.parseDouble(parts[2]);
          			respuesta=Podometro(pasos, altura);
          			break;
          		case 3: 
                                altura = Double.parseDouble(parts[1]);
          			//altura = Integer.parseInt(parts[1]);
          			//peso = Integer.parseInt(parts[2]);
                                peso = Double.parseDouble(parts[2]);
                                
          			respuesta=Imc(altura, peso);
          			break;
          		default:
          			System.out.println("Error al leer la opcion :/");
          			break;
          	}
            
			//String respuesta=yodaDo(datosRecibidos);
			//p:dato1,dato2
			
			PrintWriter outPrinter = new PrintWriter(outputStream, true);
			outPrinter.println(respuesta);			
			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}

	}
	private String Pulsometro(int edad, int pulsaciones){
		int calculoPulsMax = 220 - edad;
		String respuesta;
		int salida;
		if (pulsaciones < calculoPulsMax)
			salida = 1;
		else
			salida = 0; 

		respuesta = String.valueOf(salida);
		return respuesta;
	}

//	private String Podometro(int pasos, int altura){
        private String Podometro(int pasos, double altura){
		double longitud_paso = ((altura * 0.414)/2);
		double distancia_recorrida;
		String respuesta;
		distancia_recorrida = ((longitud_paso * pasos)/100000);
		respuesta = String.valueOf(distancia_recorrida);
		return respuesta;
	}

	//private String Imc(int altura, double peso){
        private String Imc(double altura, double peso){
		double imc = -1;
		String respuesta = "";
                System.out.println(peso+", ,"+altura);
		imc = (peso / ( altura * altura ));
                
                System.out.println("IMC - int = "+imc);
                
		respuesta = String.valueOf(imc);
                System.out.println("IMC= "+respuesta);
		return respuesta;
	}

	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
