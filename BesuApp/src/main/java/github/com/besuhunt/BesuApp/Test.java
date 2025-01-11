package github.com.besuhunt.BesuApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.besuhunt.BesuApp.constants.FacetCutAction;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Hash;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {


    static String abiFile="/home/giridharan/dev/Digital_Waste_Management/BesuApp/src/main/resources/contracts/Storage/Storage.abi";


    public static void main(String[] args) throws IOException {


        Function function = new Function(
                "init", // Function name
                Collections.emptyList(), // No input parameters
                Collections.emptyList()  // No output parameters
        );

        // Encode the function call to send it as a transaction
        String encodedFunction = FunctionEncoder.encode(function);

        System.out.println(encodedFunction);
    }


    // Helper function to convert hex string to byte array
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private void getSelector() throws IOException {

        File f= new File(abiFile);
        System.out.println(f);


        ObjectMapper obj=new ObjectMapper();
        JsonNode abiJson=obj.readTree(f);
        List<String> functionSignatures = new ArrayList<>();
        for (JsonNode entry : abiJson) {
            // Filter for functions
            if ("function".equals(entry.get("type").asText())) {
                String name = entry.get("name").asText();
                StringBuilder signatureBuilder = new StringBuilder(name).append("(");

                // Extract input types
                JsonNode inputs = entry.get("inputs");
                for (int i = 0; i < inputs.size(); i++) {
                    if (i > 0) signatureBuilder.append(",");
                    signatureBuilder.append(inputs.get(i).get("type").asText());
                }

                signatureBuilder.append(")");
                System.out.println("function "+signatureBuilder.toString()+" Signature: "+Hash.sha3(signatureBuilder.toString()).substring(0,10));

                String hash = Hash.sha3(signatureBuilder.toString());

                byte[] bty=hash.substring(0,10).getBytes();

                System.out.println(new String(bty));
//                byte[] selector = new byte[4];
//                System.arraycopy(hash, 0, selector, 0, 4);
//                System.out.println("function "+signatureBuilder.toString()+" Signature: "+bytesToHex(selector));
                functionSignatures.add(Hash.sha3(signatureBuilder.toString()).substring(0,10));


            }
        }

        functionSignatures.forEach(System.out::println);

    }


    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
