package client;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class BufferedSocketClient {

    public static void main(String args[]) throws Exception {
        Socket socket1;
        int portNumber = 1777;

        //Habilito el scanner para leer un input
        Scanner scanner = new Scanner(System.in);


        //El contenido que voy a enviar se guarda en el String
        System.out.println("Escriba el String que quiere mandar: ");

        String str = scanner.nextLine();

        socket1 = new Socket(InetAddress.getLocalHost(), portNumber);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

        PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

        pw.println(str);

        while ((str = br.readLine()) != null) {
            System.out.println(str);
            pw.println("bye");

            if (str.equals("bye"))
                break;
        }

        br.close();
        pw.close();
        socket1.close();
    }

}