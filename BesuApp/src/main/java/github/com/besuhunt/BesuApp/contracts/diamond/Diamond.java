package github.com.besuhunt.BesuApp.contracts.diamond;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Diamond extends Contract {
    public static final String BINARY = "6080604052604051611e2f380380611e2f8339818101604052810190610025919061150f565b610038816000015161005860201b60201c565b610051828260200151836040015161013560201b60201c565b5050611b93565b600061006861036460201b60201c565b905060008160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050828260030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508273ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3505050565b60005b835181101561031357600084828151811061015657610155611587565b5b6020026020010151604001519050600085838151811061017957610178611587565b5b602002602001015160000151905060008251036101cd57806040517fe767f91f0000000000000000000000000000000000000000000000000000000081526004016101c491906115c5565b60405180910390fd5b60008684815181106101e2576101e1611587565b5b602002602001015160200151905060006002811115610204576102036115e0565b5b816002811115610217576102166115e0565b5b036102315761022c828461039160201b60201c565b610303565b60016002811115610245576102446115e0565b5b816002811115610258576102576115e0565b5b036102725761026d82846106bf60201b60201c565b610302565b600280811115610285576102846115e0565b5b816002811115610298576102976115e0565b5b036102b2576102ad8284610a0b60201b60201c565b610301565b8060028111156102c5576102c46115e0565b5b6040517f7fe9a41e0000000000000000000000000000000000000000000000000000000081526004016102f8919061162b565b60405180910390fd5b5b5b5050508080600101915050610138565b507f8faa70878671ccd212d20771b795c50af8fd3ff6cf27f4bde57e5d4de0aeb673838383604051610347939291906118c2565b60405180910390a161035f8282610e9260201b60201c565b505050565b6000807fc8fcad8db84d3cc18b4c41d551ea0ee66dd599cde068d998e57d5e09332c131c90508091505090565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160361040257806040517f0ae3681c0000000000000000000000000000000000000000000000000000000081526004016103f99190611976565b60405180910390fd5b600061041261036460201b60201c565b905060008160010180549050905061044884604051806060016040528060248152602001611dbb60249139610fba60201b60201c565b60005b83518110156106b857600084828151811061046957610468611587565b5b602002602001015190506000846000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161461055f57816040517febbf5d0700000000000000000000000000000000000000000000000000000000815260040161055691906119a7565b60405180910390fd5b60405180604001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020018561ffff16815250856000016000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160000160146101000a81548161ffff021916908361ffff160217905550905050846001018290806001815401808255809150506001900390600052602060002090600891828204019190066004029091909190916101000a81548163ffffffff021916908360e01c021790555083806106a6906119ff565b9450505050808060010191505061044b565b5050505050565b60006106cf61036460201b60201c565b9050600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff160361074257816040517fcd98a96f0000000000000000000000000000000000000000000000000000000081526004016107399190611976565b60405180910390fd5b61076a83604051806060016040528060288152602001611e0760289139610fba60201b60201c565b60005b8251811015610a0557600083828151811061078b5761078a611587565b5b602002602001015190506000836000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690503073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361088057816040517f520300da00000000000000000000000000000000000000000000000000000000815260040161087791906119a7565b60405180910390fd5b8573ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036108f057816040517f358d9d1a0000000000000000000000000000000000000000000000000000000081526004016108e791906119a7565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361096157816040517f7479f93900000000000000000000000000000000000000000000000000000000815260040161095891906119a7565b60405180910390fd5b85846000016000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050808060010191505061076d565b50505050565b6000610a1b61036460201b60201c565b9050600081600101805490509050600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614610a9a57836040517fd091bc81000000000000000000000000000000000000000000000000000000008152600401610a9191906115c5565b60405180910390fd5b60005b8351811015610e8b576000848281518110610abb57610aba611587565b5b602002602001015190506000846000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191681526020019081526020016000206040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016000820160149054906101000a900461ffff1661ffff1661ffff16815250509050600073ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff1603610c1257816040517f7a08a22d000000000000000000000000000000000000000000000000000000008152600401610c0991906119a7565b60405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff1603610c8657816040517f6fafeb08000000000000000000000000000000000000000000000000000000008152600401610c7d91906119a7565b60405180910390fd5b8380610c9190611a33565b94505083816020015161ffff1614610da6576000856001018581548110610cbb57610cba611587565b5b90600052602060002090600891828204019190066004029054906101000a900460e01b90508086600101836020015161ffff1681548110610cff57610cfe611587565b5b90600052602060002090600891828204019190066004026101000a81548163ffffffff021916908360e01c02179055508160200151866000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160146101000a81548161ffff021916908361ffff160217905550505b84600101805480610dba57610db9611a5c565b5b60019003818190600052602060002090600891828204019190066004026101000a81549063ffffffff02191690559055846000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19168152602001908152602001600020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556000820160146101000a81549061ffff0219169055505050508080600101915050610a9d565b5050505050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160315610fb657610eef82604051806060016040528060288152602001611ddf60289139610fba60201b60201c565b6000808373ffffffffffffffffffffffffffffffffffffffff1683604051610f179190611ac7565b600060405180830381855af49150503d8060008114610f52576040519150601f19603f3d011682016040523d82523d6000602084013e610f57565b606091505b509150915081610fb357600081511115610f745780518082602001fd5b83836040517f192105d7000000000000000000000000000000000000000000000000000000008152600401610faa929190611ade565b60405180910390fd5b50505b5050565b6000823b9050600081036110075782826040517f919834b9000000000000000000000000000000000000000000000000000000008152600401610ffe929190611b63565b60405180910390fd5b505050565b6000604051905090565b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61106e82611025565b810181811067ffffffffffffffff8211171561108d5761108c611036565b5b80604052505050565b60006110a061100c565b90506110ac8282611065565b919050565b600067ffffffffffffffff8211156110cc576110cb611036565b5b602082029050602081019050919050565b600080fd5b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611117826110ec565b9050919050565b6111278161110c565b811461113257600080fd5b50565b6000815190506111448161111e565b92915050565b6003811061115757600080fd5b50565b6000815190506111698161114a565b92915050565b600067ffffffffffffffff82111561118a57611189611036565b5b602082029050602081019050919050565b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b6111d08161119b565b81146111db57600080fd5b50565b6000815190506111ed816111c7565b92915050565b60006112066112018461116f565b611096565b90508083825260208201905060208402830185811115611229576112286110dd565b5b835b81811015611252578061123e88826111de565b84526020840193505060208101905061122b565b5050509392505050565b600082601f83011261127157611270611020565b5b81516112818482602086016111f3565b91505092915050565b6000606082840312156112a05761129f6110e2565b5b6112aa6060611096565b905060006112ba84828501611135565b60008301525060206112ce8482850161115a565b602083015250604082015167ffffffffffffffff8111156112f2576112f16110e7565b5b6112fe8482850161125c565b60408301525092915050565b600061131d611318846110b1565b611096565b905080838252602082019050602084028301858111156113405761133f6110dd565b5b835b8181101561138757805167ffffffffffffffff81111561136557611364611020565b5b808601611372898261128a565b85526020850194505050602081019050611342565b5050509392505050565b600082601f8301126113a6576113a5611020565b5b81516113b684826020860161130a565b91505092915050565b600080fd5b600067ffffffffffffffff8211156113df576113de611036565b5b6113e882611025565b9050602081019050919050565b60005b838110156114135780820151818401526020810190506113f8565b60008484015250505050565b600061143261142d846113c4565b611096565b90508281526020810184848401111561144e5761144d6113bf565b5b6114598482856113f5565b509392505050565b600082601f83011261147657611475611020565b5b815161148684826020860161141f565b91505092915050565b6000606082840312156114a5576114a46110e2565b5b6114af6060611096565b905060006114bf84828501611135565b60008301525060206114d384828501611135565b602083015250604082015167ffffffffffffffff8111156114f7576114f66110e7565b5b61150384828501611461565b60408301525092915050565b6000806040838503121561152657611525611016565b5b600083015167ffffffffffffffff8111156115445761154361101b565b5b61155085828601611391565b925050602083015167ffffffffffffffff8111156115715761157061101b565b5b61157d8582860161148f565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b6115bf8161110c565b82525050565b60006020820190506115da60008301846115b6565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b600060ff82169050919050565b6116258161160f565b82525050565b6000602082019050611640600083018461161c565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b61167b8161110c565b82525050565b60038110611692576116916115e0565b5b50565b60008190506116a382611681565b919050565b60006116b382611695565b9050919050565b6116c3816116a8565b82525050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6116fe8161119b565b82525050565b600061171083836116f5565b60208301905092915050565b6000602082019050919050565b6000611734826116c9565b61173e81856116d4565b9350611749836116e5565b8060005b8381101561177a5781516117618882611704565b975061176c8361171c565b92505060018101905061174d565b5085935050505092915050565b600060608301600083015161179f6000860182611672565b5060208301516117b260208601826116ba565b50604083015184820360408601526117ca8282611729565b9150508091505092915050565b60006117e38383611787565b905092915050565b6000602082019050919050565b600061180382611646565b61180d8185611651565b93508360208202850161181f85611662565b8060005b8581101561185b578484038952815161183c85826117d7565b9450611847836117eb565b925060208a01995050600181019050611823565b50829750879550505050505092915050565b600081519050919050565b600082825260208201905092915050565b60006118948261186d565b61189e8185611878565b93506118ae8185602086016113f5565b6118b781611025565b840191505092915050565b600060608201905081810360008301526118dc81866117f8565b90506118eb60208301856115b6565b81810360408301526118fd8184611889565b9050949350505050565b600082825260208201905092915050565b6000611923826116c9565b61192d8185611907565b9350611938836116e5565b8060005b838110156119695781516119508882611704565b975061195b8361171c565b92505060018101905061193c565b5085935050505092915050565b600060208201905081810360008301526119908184611918565b905092915050565b6119a18161119b565b82525050565b60006020820190506119bc6000830184611998565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061ffff82169050919050565b6000611a0a826119f1565b915061ffff8203611a1e57611a1d6119c2565b5b600182019050919050565b6000819050919050565b6000611a3e82611a29565b915060008203611a5157611a506119c2565b5b600182039050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603160045260246000fd5b600081905092915050565b6000611aa18261186d565b611aab8185611a8b565b9350611abb8185602086016113f5565b80840191505092915050565b6000611ad38284611a96565b915081905092915050565b6000604082019050611af360008301856115b6565b8181036020830152611b058184611889565b90509392505050565b600081519050919050565b600082825260208201905092915050565b6000611b3582611b0e565b611b3f8185611b19565b9350611b4f8185602086016113f5565b611b5881611025565b840191505092915050565b6000604082019050611b7860008301856115b6565b8181036020830152611b8a8184611b2a565b90509392505050565b61021980611ba26000396000f3fe60806040523661000b57005b6000807fc8fcad8db84d3cc18b4c41d551ea0ee66dd599cde068d998e57d5e09332c131c9050809150600082600001600080357fffffffff00000000000000000000000000000000000000000000000000000000167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610167576000357fffffffff00000000000000000000000000000000000000000000000000000000166040517f5416eb9800000000000000000000000000000000000000000000000000000000815260040161015e91906101c8565b60405180910390fd5b3660008037600080366000845af43d6000803e8060008114610188573d6000f35b3d6000fd5b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b6101c28161018d565b82525050565b60006020820190506101dd60008301846101b9565b9291505056fea264697066735822122066427217f44c5e918c6b40b9e9cddc107ecd16c2e982207aaca92ba31686b6ba64736f6c634300081a00334c69624469616d6f6e644375743a2041646420666163657420686173206e6f20636f64654c69624469616d6f6e644375743a205f696e6974206164647265737320686173206e6f20636f64654c69624469616d6f6e644375743a205265706c61636520666163657420686173206e6f20636f6465";

    private static String librariesLinkedBinary;

    public static final Event DIAMONDCUT_EVENT = new Event("DiamondCut", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<FacetCut>>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Diamond(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Diamond(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Diamond(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Diamond(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DiamondCutEventResponse> getDiamondCutEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DIAMONDCUT_EVENT, transactionReceipt);
        ArrayList<DiamondCutEventResponse> responses = new ArrayList<DiamondCutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DiamondCutEventResponse typedResponse = new DiamondCutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._diamondCut = (List<FacetCut>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._init = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._calldata = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DiamondCutEventResponse getDiamondCutEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIAMONDCUT_EVENT, log);
        DiamondCutEventResponse typedResponse = new DiamondCutEventResponse();
        typedResponse.log = log;
        typedResponse._diamondCut = (List<FacetCut>) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse._init = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse._calldata = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<DiamondCutEventResponse> diamondCutEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiamondCutEventFromLog(log));
    }

    public Flowable<DiamondCutEventResponse> diamondCutEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIAMONDCUT_EVENT));
        return diamondCutEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    @Deprecated
    public static Diamond load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Diamond(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Diamond load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Diamond(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Diamond load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Diamond(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Diamond load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Diamond(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Diamond> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger initialWeiValue,
            List<FacetCut> _diamondCut, DiamondArgs _args) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<FacetCut>(FacetCut.class, _diamondCut), 
                _args));
        return deployRemoteCall(Diamond.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<Diamond> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, BigInteger initialWeiValue,
            List<FacetCut> _diamondCut, DiamondArgs _args) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<FacetCut>(FacetCut.class, _diamondCut), 
                _args));
        return deployRemoteCall(Diamond.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Diamond> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue,
            List<FacetCut> _diamondCut, DiamondArgs _args) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<FacetCut>(FacetCut.class, _diamondCut), 
                _args));
        return deployRemoteCall(Diamond.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Diamond> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue,
            List<FacetCut> _diamondCut, DiamondArgs _args) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<FacetCut>(FacetCut.class, _diamondCut), 
                _args));
        return deployRemoteCall(Diamond.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor, initialWeiValue);
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

    public static class FacetCut extends DynamicStruct {
        public String facetAddress;

        public BigInteger action;

        public List<byte[]> functionSelectors;

        public FacetCut(String facetAddress, BigInteger action, List<byte[]> functionSelectors) {
            super(new org.web3j.abi.datatypes.Address(160, facetAddress), 
                    new org.web3j.abi.datatypes.generated.Uint8(action), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes4>(
                            org.web3j.abi.datatypes.generated.Bytes4.class,
                            org.web3j.abi.Utils.typeMap(functionSelectors, org.web3j.abi.datatypes.generated.Bytes4.class)));
            this.facetAddress = facetAddress;
            this.action = action;
            this.functionSelectors = functionSelectors;
        }

        public FacetCut(Address facetAddress, Uint8 action,
                @Parameterized(type = Bytes4.class) DynamicArray<Bytes4> functionSelectors) {
            super(facetAddress, action, functionSelectors);
            this.facetAddress = facetAddress.getValue();
            this.action = action.getValue();
            this.functionSelectors = functionSelectors.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
    }

    public static class DiamondArgs extends DynamicStruct {
        public String owner;

        public String init;

        public byte[] initCalldata;

        public DiamondArgs(String owner, String init, byte[] initCalldata) {
            super(new org.web3j.abi.datatypes.Address(160, owner), 
                    new org.web3j.abi.datatypes.Address(160, init), 
                    new org.web3j.abi.datatypes.DynamicBytes(initCalldata));
            this.owner = owner;
            this.init = init;
            this.initCalldata = initCalldata;
        }

        public DiamondArgs(Address owner, Address init, DynamicBytes initCalldata) {
            super(owner, init, initCalldata);
            this.owner = owner.getValue();
            this.init = init.getValue();
            this.initCalldata = initCalldata.getValue();
        }
    }

    public static class DiamondCutEventResponse extends BaseEventResponse {
        public List<FacetCut> _diamondCut;

        public String _init;

        public byte[] _calldata;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
