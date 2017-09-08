import java.util.Scanner;

/**
 * Created by itsmebadr on 8/27/17.
 */



public class CaesarCipher {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";


    /**
     * Function for encrypting plaintext using a Caesar cipher
     * @param plainText - String of plaintext to encrypt
     * @param shift - int to shift the Caesar cipher for encryption
     * @return encrypted - string of encrypted cipher text
     */
    public static String encrypt(String plainText, int shift){

        plainText = plainText.toLowerCase(); //convert all of the plaintext to lowercase
        plainText = plainText.replaceAll("\\s+", ""); //Eliminate spaces
        String encrypted = ""; //Empty string to be filled with encrypted cipher text
        for (int i = 0; i < plainText.length(); i++){ //for-loop that contains the encryption process
            int pos = ALPHABET.indexOf(plainText.charAt(i)); //Position starts at 'A'
            int formula = (pos + shift) % 26; //Caesar cipher encryption formula
            if(formula < 0){ //checking for negatives
                formula = ALPHABET.length() + formula;
            }
            char newLetter = ALPHABET.charAt(formula); //First encrypted character
            encrypted += newLetter; //Add encrypted character to the cipher text string
        }
        encrypted = encrypted.toUpperCase(); //Convert all the characters to uppercase
        return encrypted; //Returns fully encrypted cipher text

    }


    /**
     * Function for decrypting ciphertext using Caesar cipher
     * @param cipherText - String of ciphertext to decrypt
     * @param shift - int to shift the Caesar cipher for decryption
     * @return decrypted - String of decrypted plain text
     */
    public static String decrypt(String cipherText, int shift){

        cipherText = cipherText.toLowerCase(); //convert all of the cipher text to lowercase
        String decrypted = ""; //Empty string to be filled with decrypted  plaintext
        for(int i = 0; i < cipherText.length(); i++){ //for-loop that contains the decryption process
            int pos = ALPHABET.indexOf(cipherText.charAt(i)); //Position starts at 'A'
            int formula = (pos - shift) % 26; //Caesar cipher decryption formula
            if(formula < 0){ //Checking for negatives
                formula = ALPHABET.length() + formula;
            }
            char newLetter = ALPHABET.charAt(formula); //First decrypted character
            decrypted += newLetter; //Add decrypted character to the plaintext string
            //shift++; This was only used to solve Problem 1 part c on the homework
        }

        return decrypted; //Return fully decrypted plaintext
    }


    /**
     * Main method that prompts the user to either encrypt a message
     * or decrypt a message using a Caesar cipher
     * @param args
     */
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Press 1 to encrypt a message");
        System.out.println("Press 2 to decrypt a message");
        int i = scan.nextInt();

        if(i == 1){
            scan.nextLine();
            System.out.println("Please enter a message to encrypt");
            String encryptMe;
            encryptMe = scan.nextLine();
            System.out.println("Please enter a shifting number (negative for counter clockwise)");
            int shiftMe;
            shiftMe = scan.nextInt();
            String encrypted = encrypt(encryptMe, shiftMe);
            System.out.println("Encrypted text: " + encrypted);
            scan.close();
        }

        else if(i == 2){
            System.out.println("Please enter a message to decrypt");
            String decryptMe;
            decryptMe = scan.next();
            System.out.println("Please enter a shifting number (negative for counter clockwise)");
            int shiftMe;
            shiftMe = scan.nextInt();
            String decrypted = decrypt(decryptMe, shiftMe);
            System.out.println("Decrypted text: " + decrypted);
            scan.close();
        }

        else{
            System.out.println("Invalid Entry");
        }

    }


}

