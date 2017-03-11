import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;


public class ProcesadorYodafy extends Thread {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	private OutputStream outputStream;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	//Añadido el metodo run:
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

			String respuesta=yodaDo(datosRecibidos);
			
			PrintWriter outPrinter = new PrintWriter(outputStream, true);
			outPrinter.println(respuesta);			
			outPrinter.println(respuesta);
		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}

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
