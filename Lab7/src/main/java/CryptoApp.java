import javazaawansowana.lab1.GreedyAlgorithm;
import javazaawansowana.lab1.Item;
import javazaawansowana.lab1.RandomAlgorithm;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CryptoApp {
    private final String CHOICE_MESSAGE = "1. Rozwiąż instancję problemu plecakowego algorytmem zachłannym\n" +
                                            "2. Rozwiąż instancję problemu plecakowego algorytmem losowym\n" +
                                            "3. Generuj przedmioty\n" +
                                            "4. Pokaz przedmioty\n" +
                                            "5. Zmien wielkosc plecaka\n" +
                                            "6. Zapisz do pliku\n" +
                                            "7. Odczytaj z pliku\n" +
                                            "5. Zaszyfruj plik z danymi\n" +
                                            "6. Deszyfruj plik z danymi\n" +
                                            "0. Wyjście";

    private ArrayList<Item> listOfItems = new ArrayList<>();
    private int knapsackSize = 0;
    Cipher cipher = Cipher.getInstance("RSA");
    PrivateKey privateKey;
    PublicKey publicKey;

    Scanner scanner = new Scanner(System.in);


    public CryptoApp() throws Exception {
        int choice = -1;

        readKeys();

        while(true){
            System.out.println(CHOICE_MESSAGE);
            try {
                choice = Integer.parseInt(scanner.next());
            }catch(Exception e){
                System.out.println("\nZły wybór!");
            }

            switch(choice){
                case 1:
                    greedyAlgorithm();
                    break;
                case 2:
                    randomAlgorithm();
                    break;
                case 3:
                    generateItems();
                    break;
                case 4:
                    printAllItems();
                    break;
                case 5:
                    resizeKnapsack();
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    readFromFile();
                    break;
                case 8:
                    encryptFile();
                    break;
                case 9:
                    decryptFile();
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    private void readKeys(){
        try {
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/ssh/private_key.der"));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);

            byte[] publicKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/ssh/public_key.der"));
            X509EncodedKeySpec specPub = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory kfPub = KeyFactory.getInstance("RSA");
            publicKey = kfPub.generatePublic(specPub);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void greedyAlgorithm(){
        GreedyAlgorithm ga = new GreedyAlgorithm(listOfItems, knapsackSize);
        ga.solve();
        System.out.println(ga.description());
    }

    private void randomAlgorithm(){
        RandomAlgorithm ra = new RandomAlgorithm(listOfItems, knapsackSize, 1000);
        ra.solve();
        System.out.println(ra.description());
    }

    private void generateItems(){
        int itemsAmount;
        System.out.println("\nIle przedmiotów wygenerować?");
        try {
            Random random = new Random();
            itemsAmount = Integer.parseInt(scanner.next());
            if (itemsAmount < 0 || itemsAmount > 100){
                System.out.println("Wprowdzono błędą liczbę. Ustawiam liczbę przedmiotów na 15");
                itemsAmount = 15;
            }
            for(int i = 0; i < itemsAmount; i++)
                listOfItems.add(new Item("Item" + i, random.nextInt() % 500 + 500, random.nextDouble() * 1000));
        }catch(Exception e){
            System.out.println("\nNie liczba!");
        }
    }

    private void printAllItems(){
        System.out.println("Nazwa: Waga: Wartosc:");
        for(Item item : listOfItems)
            System.out.println(item.getName() + " " + item.getWeight() + " " + item.getItemValue());
    }

    private void resizeKnapsack(){
        try {
            knapsackSize = Integer.parseInt(scanner.next());
            if (knapsackSize < 0 || knapsackSize > 10000){
                System.out.println("Wprowdzono błędą liczbę. Ustawiam liczbę przedmiotów na 150");
                knapsackSize = 150;
            }
        }catch(Exception e){
            System.out.println("Nie liczba!");
        }
    }

    private void saveToFile(){
        try {
            File file = new File("src/main/resources/fileData.txt");

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write("@CAPACITY@," + knapsackSize + "\n");
            for (Item item : listOfItems)
                bw.write("@ITEM@," + item.getName() + "," + item.getWeight() + "," + item.getItemValue() + "\n");
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void readFromFile(){
        try {
            File file = new File("src/main/resources/fileData.txt");
            if(!file.exists()) {
                System.out.println("Plik nie istnieje!");
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts[0].equals("@CAPACITY"))
                    knapsackSize = Integer.parseInt(parts[1]);
                else if(parts[0].equals("@ITEM@")){
                    listOfItems.add(new Item(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void encryptFile(){
        File encryptedFile = new File("src/main/resources/fileCrypt.txt");
        File toEncrypt = new File("src/main/resources/fileData.txt");

        byte[] rawBytes = new byte[245];
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            FileInputStream fInput = new FileInputStream(toEncrypt);
            FileOutputStream fOutput = new FileOutputStream(encryptedFile);
            int read = 0;
            while (read != -1) {

                read = fInput.read(rawBytes);
                if (read == 245) {
                    fOutput.write(cipher.doFinal(rawBytes));
                } else if (read != -1) {
                    byte[] sliceByte = Arrays.copyOfRange(rawBytes, 0, read);
                    fOutput.write(cipher.doFinal(sliceByte));
                }

            }
            fInput.close();
            fOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void decryptFile(){
        File toDecrypt = new File("src/main/resources/fileCrypt.txt");
        File decryptedFile = new File("src/main/resources/fileData.txt");

        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] rawBytes = new byte[256];
            FileInputStream fInput = new FileInputStream(toDecrypt);
            FileOutputStream fOutput = new FileOutputStream(decryptedFile);
            int read = 0;

            while (read != -1) {

                read = fInput.read(rawBytes);
                if (read == (256)) {
                    fOutput.write(cipher.doFinal(rawBytes));
                } else if (read != -1) {
                    byte[] sliceByte = Arrays.copyOfRange(rawBytes, 0, read);
                    fOutput.write(cipher.doFinal(sliceByte));
                }

            }
            fInput.close();
            fOutput.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String args[]){
        try {
            new CryptoApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
