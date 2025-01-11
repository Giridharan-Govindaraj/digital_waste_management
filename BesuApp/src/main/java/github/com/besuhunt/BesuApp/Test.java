package github.com.besuhunt.BesuApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.Hash;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {


    static String abiFile="/home/giridharan/dev/Digital_Waste_Management/BesuApp/src/main/resources/contracts/Storage/Storage.abi";


    public static void main(String[] args) throws IOException {


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
                functionSignatures.add(Hash.sha3(signatureBuilder.toString()).substring(0,10));


            }
        }

        functionSignatures.forEach(System.out::println);

    }




    private void getSelectors(){


    }
}
