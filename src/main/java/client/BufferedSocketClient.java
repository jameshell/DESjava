package client;

import javax.crypto.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

        byte[] text;
        byte[] textEncrypted;
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            text = str.getBytes();
            textEncrypted = desCipher.doFinal(text);

            System.out.println("Representacion en Bytes texto ORIGINAL ---> "+text);
            System.out.println("Representacion en Bytes texto CIFRADO ---> "+textEncrypted);

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

        }catch(NoSuchAlgorithmException e){
        e.printStackTrace();
        }catch(NoSuchPaddingException e){
        e.printStackTrace();
        }catch(InvalidKeyException e){
        e.printStackTrace();
        }catch(IllegalBlockSizeException e){
        e.printStackTrace();
        }catch(BadPaddingException e){
        e.printStackTrace();
        }


    }

}