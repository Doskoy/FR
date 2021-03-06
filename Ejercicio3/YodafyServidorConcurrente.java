import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class YodafyServidorConcurrente {

	public static void main(String[] args) {
		ServerSocket socketServidor;
		// Puerto de escucha
		int port=8989;

		try {
			
			socketServidor = new ServerSocket (port);
			do {
				Socket socketServicio = socketServidor.accept();
				
				ProcesadorYodafy procesador = new ProcesadorYodafy(socketServicio);
				procesador.run();
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
