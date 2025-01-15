package github.com.besuhunt.BesuApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.besuhunt.BesuApp.contracts.diamond.facets.DiamondLoupeFacet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Test {

    private static final String FUNCTION="function";
    private static final String TYPE="type";
    private static final String INPUTS="inputs";
    private static final String NAME="name";


    public static void  main(String[] args) throws IOException {


        String out="0x00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000e000000000000000000000000000000000000000000000000000000000000001e0000000000000000000000000f12b5dd4ead5f743c6baa640b0216200e89b60da000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000011f931c1c00000000000000000000000000000000000000000000000000000000000000000000000000000000345ca3e014aaf5dca488057592ee47305d9b3e1000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000005cdffacc60000000000000000000000000000000000000000000000000000000052ef6b2c00000000000000000000000000000000000000000000000000000000adfca15e000000000000000000000000000000000000000000000000000000007a0ed6270000000000000000000000000000000000000000000000000000000001ffc9a700000000000000000000000000000000000000000000000000000000000000000000000000000000f25186b5081ff5ce73482ad761db0eb0d25abfbf000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000028da5cb5b00000000000000000000000000000000000000000000000000000000f2fde38b00000000000000000000000000000000000000000000000000000000";
        final Function function = new Function("facets",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<DiamondLoupeFacet.Facet>>() {}));

        System.out.println();

        List<Type> decoded =  FunctionReturnDecoder.decode(out, function.getOutputParameters());

        List<DiamondLoupeFacet.Facet> facets = new ArrayList<>();
        if (!decoded.isEmpty() && decoded.get(0) instanceof DynamicArray) {
            DynamicArray<?> dynamicArray = (DynamicArray<?>) decoded.get(0);

            for (Object element : dynamicArray.getValue()) {

                System.out.println(element);
                List<DiamondLoupeFacet.Facet> structFields = Collections.singletonList((DiamondLoupeFacet.Facet) element);
                facets.add(structFields.get(0));
            }
        }

        facets.stream().forEach(f->{
            System.out.println(f.facetAddress);
            f.functionSelectors.stream().forEach(s->{
                System.out.println(Numeric.toHexString(s));
            });
        });

    }

    private static <S extends Type, T> List<T> convertToNative(List<S> arr) {
        List<T> out = new ArrayList();

        for(S s : arr) {
            if (StructType.class.isAssignableFrom(s.getClass())) {
                out.add((T) s);
            } else {
                out.add((T) s.getValue());
            }
        }

        return out;
    }


    private static void getSelectors(String file) throws IOException {
        JSONObject jsonObj=new JSONObject(new String(Files.readAllBytes(Paths.get(file))));
        JSONArray abi=null;
        JSONObject hashes=null;
        List<Bytes4> functionSignatures = new ArrayList<>();

        if(jsonObj.has("abi")){
            abi=jsonObj.getJSONArray("abi");
        }
        if(jsonObj.has("hashes")){
            hashes=jsonObj.getJSONObject("hashes");
        }

        for(Object key:abi){
            JSONObject obj=(JSONObject) key;
            if(obj.has(TYPE) && (obj.getString(TYPE).equals(FUNCTION))){
                for (String func:hashes.keySet()){
                    if(func.contains(obj.getString(NAME))){
                        byte[] byteArray= Numeric.hexStringToByteArray(hashes.getString(func));
                        if (byteArray.length != 4) {
                            throw new IllegalArgumentException("Hex string must represent exactly 4 bytes");
                        }
                        // Create Bytes4 object and add to list
                        Bytes4 bytes4Selector = new Bytes4(byteArray);
                        functionSignatures.add(bytes4Selector);
                        break;
                    }
                }

            }
        }

//        for (JsonNode entry : json) {
//
//            System.out.println(entry);

//            if (FUNCTION.equals(entry.get(TYPE).asText())) {
//                String name = entry.get(NAME).asText();
//                StringBuilder signatureBuilder = new StringBuilder(name).append("(");
//                JsonNode inputs = entry.get(INPUTS);
//
//                System.out.println("inputs:"+inputs);
//                for (int i = 0; i < inputs.size(); i++) {
//                    if (i > 0) signatureBuilder.append(",");
//                    System.out.println(inputs.get(i).get(TYPE).asText());
//                    signatureBuilder.append(inputs.get(i).get(TYPE).asText());
//                }
//                signatureBuilder.append(")");
//
//                String hexString= Hash.sha3(signatureBuilder.toString()).substring(0,10);
//
//                System.out.println(signatureBuilder.toString()+":"+hexString);

//
//               // System.out.println(signatureBuilder.toString()+":"+Numeric.toHexString(bytes4Selector.getValue()));
//                functionSignatures.add(bytes4Selector);
//            }
//        }


    }



}
