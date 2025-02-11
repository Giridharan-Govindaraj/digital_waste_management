package github.com.besuhunt.BesuApp.contracts.diamond.facets;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.12.3.
 */
@SuppressWarnings("rawtypes")
public class DiamondLoupeFacet extends Contract {
    public static final String BINARY = "6080604052348015600f57600080fd5b5061116e8061001f6000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806301ffc9a71461005c57806352ef6b2c1461008c5780637a0ed627146100aa578063adfca15e146100c8578063cdffacc6146100f8575b600080fd5b61007660048036038101906100719190610bb9565b610128565b6040516100839190610c01565b60405180910390f35b61009461019e565b6040516100a19190610d0c565b60405180910390f35b6100b26103c0565b6040516100bf9190610eeb565b60405180910390f35b6100e260048036038101906100dd9190610f39565b61088c565b6040516100ef9190610fd5565b60405180910390f35b610112600480360381019061010d9190610bb9565b610a73565b60405161011f9190611006565b60405180910390f35b600080610133610aff565b9050806002016000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060009054906101000a900460ff16915050919050565b606060006101aa610aff565b90506000816001018054905090508067ffffffffffffffff8111156101d2576101d1611021565b5b6040519080825280602002602001820160405280156102005781602001602082028036833780820191505090505b5092506000805b828110156103b657600084600101828154811061022757610226611050565b5b90600052602060002090600891828204019190066004029054906101000a900460e01b90506000856000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000805b85811015610336578881815181106102e6576102e5611050565b5b602002602001015173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16036103295760019150610336565b80806001019150506102cb565b50801561034957600090505050506103a9565b8188868151811061035d5761035c611050565b5b602002602001019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505084806103a2906110b8565b9550505050505b8080600101915050610207565b5080845250505090565b606060006103cc610aff565b90506000816001018054905090508067ffffffffffffffff8111156103f4576103f3611021565b5b60405190808252806020026020018201604052801561042d57816020015b61041a610b2c565b8152602001906001900390816104125790505b50925060008167ffffffffffffffff81111561044c5761044b611021565b5b60405190808252806020026020018201604052801561047a5781602001602082028036833780820191505090505b5090506000805b8381101561081d5760008560010182815481106104a1576104a0611050565b5b90600052602060002090600891828204019190066004029054906101000a900460e01b90506000866000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690506000805b85811015610684578273ffffffffffffffffffffffffffffffffffffffff168a828151811061057757610576611050565b5b60200260200101516000015173ffffffffffffffffffffffffffffffffffffffff160361067757838a82815181106105b2576105b1611050565b5b6020026020010151602001518883815181106105d1576105d0611050565b5b602002602001015161ffff16815181106105ee576105ed611050565b5b60200260200101907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191690817bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19168152505086818151811061064d5761064c611050565b5b6020026020010180518091906106629061110e565b61ffff1661ffff168152505060019150610684565b8080600101915050610545565b5080156106975760009050505050610810565b818986815181106106ab576106aa611050565b5b60200260200101516000019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508667ffffffffffffffff81111561070357610702611021565b5b6040519080825280602002602001820160405280156107315781602001602082028036833780820191505090505b5089868151811061074557610744611050565b5b6020026020010151602001819052508289868151811061076857610767611050565b5b60200260200101516020015160008151811061078757610786611050565b5b60200260200101907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191690817bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19168152505060018686815181106107e8576107e7611050565b5b602002602001019061ffff16908161ffff16815250508480610809906110b8565b9550505050505b8080600101915050610481565b5060005b8181101561088157600083828151811061083e5761083d611050565b5b602002602001015161ffff169050600087838151811061086157610860611050565b5b602002602001015160200151905081815250508080600101915050610821565b508085525050505090565b60606000610898610aff565b905060008160010180549050905060008167ffffffffffffffff8111156108c2576108c1611021565b5b6040519080825280602002602001820160405280156108f05781602001602082028036833780820191505090505b50935060005b82811015610a6757600084600101828154811061091657610915611050565b5b90600052602060002090600891828204019190066004029054906101000a900460e01b90506000856000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff168873ffffffffffffffffffffffffffffffffffffffff1603610a5857818785815181106109fd576109fc611050565b5b60200260200101907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191690817bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815250508380610a54906110b8565b9450505b505080806001019150506108f6565b50808452505050919050565b600080610a7e610aff565b9050806000016000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b6000807fc8fcad8db84d3cc18b4c41d551ea0ee66dd599cde068d998e57d5e09332c131c90508091505090565b6040518060400160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001606081525090565b600080fd5b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b610b9681610b61565b8114610ba157600080fd5b50565b600081359050610bb381610b8d565b92915050565b600060208284031215610bcf57610bce610b5c565b5b6000610bdd84828501610ba4565b91505092915050565b60008115159050919050565b610bfb81610be6565b82525050565b6000602082019050610c166000830184610bf2565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610c7382610c48565b9050919050565b610c8381610c68565b82525050565b6000610c958383610c7a565b60208301905092915050565b6000602082019050919050565b6000610cb982610c1c565b610cc38185610c27565b9350610cce83610c38565b8060005b83811015610cff578151610ce68882610c89565b9750610cf183610ca1565b925050600181019050610cd2565b5085935050505092915050565b60006020820190508181036000830152610d268184610cae565b905092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b610d8f81610b61565b82525050565b6000610da18383610d86565b60208301905092915050565b6000602082019050919050565b6000610dc582610d5a565b610dcf8185610d65565b9350610dda83610d76565b8060005b83811015610e0b578151610df28882610d95565b9750610dfd83610dad565b925050600181019050610dde565b5085935050505092915050565b6000604083016000830151610e306000860182610c7a565b5060208301518482036020860152610e488282610dba565b9150508091505092915050565b6000610e618383610e18565b905092915050565b6000602082019050919050565b6000610e8182610d2e565b610e8b8185610d39565b935083602082028501610e9d85610d4a565b8060005b85811015610ed95784840389528151610eba8582610e55565b9450610ec583610e69565b925060208a01995050600181019050610ea1565b50829750879550505050505092915050565b60006020820190508181036000830152610f058184610e76565b905092915050565b610f1681610c68565b8114610f2157600080fd5b50565b600081359050610f3381610f0d565b92915050565b600060208284031215610f4f57610f4e610b5c565b5b6000610f5d84828501610f24565b91505092915050565b600082825260208201905092915050565b6000610f8282610d5a565b610f8c8185610f66565b9350610f9783610d76565b8060005b83811015610fc8578151610faf8882610d95565b9750610fba83610dad565b925050600181019050610f9b565b5085935050505092915050565b60006020820190508181036000830152610fef8184610f77565b905092915050565b61100081610c68565b82525050565b600060208201905061101b6000830184610ff7565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000819050919050565b60006110c3826110ae565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036110f5576110f461107f565b5b600182019050919050565b600061ffff82169050919050565b600061111982611100565b915061ffff820361112d5761112c61107f565b5b60018201905091905056fea2646970667358221220bc09756fcf27197b3d202850ac32967181e1761560170df38024afe4d7d1795464736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_FACETADDRESS = "facetAddress";

    public static final String FUNC_FACETADDRESSES = "facetAddresses";

    public static final String FUNC_FACETFUNCTIONSELECTORS = "facetFunctionSelectors";

    public static final String FUNC_FACETS = "facets";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    @Deprecated
    protected DiamondLoupeFacet(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DiamondLoupeFacet(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DiamondLoupeFacet(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DiamondLoupeFacet(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> facetAddress(byte[] _functionSelector) {
        final Function function = new Function(FUNC_FACETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(_functionSelector)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> facetAddresses() {
        final Function function = new Function(FUNC_FACETADDRESSES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> facetFunctionSelectors(String _facet) {
        final Function function = new Function(FUNC_FACETFUNCTIONSELECTORS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _facet)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes4>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> facets() {
        final Function function = new Function(FUNC_FACETS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Facet>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] _interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(_interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static DiamondLoupeFacet load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DiamondLoupeFacet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DiamondLoupeFacet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DiamondLoupeFacet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DiamondLoupeFacet load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DiamondLoupeFacet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DiamondLoupeFacet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DiamondLoupeFacet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DiamondLoupeFacet> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DiamondLoupeFacet.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DiamondLoupeFacet> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DiamondLoupeFacet.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<DiamondLoupeFacet> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DiamondLoupeFacet.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DiamondLoupeFacet> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DiamondLoupeFacet.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Facet extends DynamicStruct {
        public String facetAddress;

        public List<byte[]> functionSelectors;

        public Facet(String facetAddress, List<byte[]> functionSelectors) {
            super(new org.web3j.abi.datatypes.Address(160, facetAddress), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes4>(
                            org.web3j.abi.datatypes.generated.Bytes4.class,
                            org.web3j.abi.Utils.typeMap(functionSelectors, org.web3j.abi.datatypes.generated.Bytes4.class)));
            this.facetAddress = facetAddress;
            this.functionSelectors = functionSelectors;
        }

        public Facet(Address facetAddress,
                @Parameterized(type = Bytes4.class) DynamicArray<Bytes4> functionSelectors) {
            super(facetAddress, functionSelectors);
            this.facetAddress = facetAddress.getValue();
            this.functionSelectors = functionSelectors.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }


    }
}
