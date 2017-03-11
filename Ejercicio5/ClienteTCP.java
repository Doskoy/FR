import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTCP {

	public static void main(String[] args) throws IOException {
		
		String buferEnvio = "";
		String buferRecepcion = "";
		String host="localhost";
		int port=8989;
		int edad = -1;
		double peso = -1;
		double altura = -1;
		String imc="";
		int opcionMenu=-1;
		int pulsacionesMax=-1;
		int pasos=-1;
		boolean salir = false;
		Socket socketServicio=null;

		InputStream inputStream = null;
		OutputStream outputStream = null;
		PrintWriter outPrinter = null;
		BufferedReader inReader = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			socketServicio = new Socket (host, port);		
			
			inputStream = socketServicio.getInputStream();
			outputStream = socketServicio.getOutputStream();
			
			outPrinter = new PrintWriter(outputStream, true);
			
			inReader = new BufferedReader(new InputStreamReader(inputStream));

		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) { 
			System.err.println("Error de entrada/salida al abrir el socket.");
		}

                System.out.println("Introduce tu edad: ");
                edad = sc.nextInt();
                System.out.println("Introduce tu peso: ");
                peso = sc.nextDouble();
                System.out.println("Introduce tu altura en metros: ");
                altura = sc.nextDouble();
                
                System.out.println("altura: "+altura);

		do{
			System.out.println("-------------------Monitor de Actividad-------------------");
			System.out.println(" 1 -> Pulsometro");
			System.out.println(" 2 -> Podometro");
			System.out.println(" 3 -> IMC");
			System.out.println("-1 -> Salir"); 
			opcionMenu = sc.nextInt();

			switch (opcionMenu) {
				case 1:		System.out.println("Introduce tus pulsaciones maximas realizando un ejercicio muy intenso: ");
							pulsacionesMax = sc.nextInt();
							buferEnvio = "" + opcionMenu + "," + edad + "," + pulsacionesMax;
							outPrinter.println(buferEnvio);
							outPrinter.flush();
							buferRecepcion = inReader.readLine();
							if (buferRecepcion == "0"){
								System.out.println("Tus pulsaciones son demasiado altas, intenta hacer ejercicio mas suave");
							}else 
								System.out.println("Tus pulsaciones estan en el rango correcto :)");
							break;

				case 2: 	System.out.println("Introduce los pasos que has realizado hoy: ");
							pasos = sc.nextInt();

							buferEnvio = "" + opcionMenu + "," + pasos + "," + altura;
							outPrinter.println(buferEnvio);
							outPrinter.flush();
							buferRecepcion = inReader.readLine();

							String distancia = buferRecepcion;
							System.out.println("Has recorrido" + distancia + " Km");
							break;

				case 3: 	buferEnvio = "" + opcionMenu + "," + altura + "," + peso;
							outPrinter.println(buferEnvio);
							outPrinter.flush();
							buferRecepcion = inReader.readLine();
							imc = buferRecepcion;

							System.out.println("Tu IMC es de " + imc);
							//int imc1 = Integer.parseInt(imc);
                                                        double imc1 = Double.parseDouble(imc);
							if (imc1 <= 18.5) 
								System.out.println("Te encuentras en una situacion de Infrapeso");
							else if (imc1 > 18.5 && imc1 < 25) 
								 System.out.println("Te encuentras en tu Peso normal");
								 else if (imc1 >= 25 && imc1 < 30)
								 	  System.out.println("Te encuentras en una situacion de Sobrepeso");
								 	  else if (imc1 >= 30)
								 	  	   System.out.println("Deja de comer, eres un Obeso");
			 	salir = true;
							break;

				default: 	if (opcionMenu == -1) 
							salir = true; 
							else 
							System.out.println("No existe esa opcion. Intentalo otravez");
				break;
			}

		}while(!salir);


	}

}
