package github.com.besuhunt.BesuApp.contracts.diamond.facets;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class DiamondCutFacet extends Contract {
    public static final String BINARY = "6080604052348015600f57600080fd5b50611c048061001f6000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80631f931c1c14610030575b600080fd5b61004a60048036038101906100459190611148565b61004c565b005b6100546100b6565b6100af85859061006491906114e6565b8484848080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050610180565b5050505050565b6100be610397565b60030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461017e573361011d610397565b60030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166040517fff4127cb00000000000000000000000000000000000000000000000000000000815260040161017592919061150a565b60405180910390fd5b565b60005b835181101561034c5760008482815181106101a1576101a0611533565b5b602002602001015160400151905060008583815181106101c4576101c3611533565b5b6020026020010151600001519050600082510361021857806040517fe767f91f00000000000000000000000000000000000000000000000000000000815260040161020f9190611562565b60405180910390fd5b600086848151811061022d5761022c611533565b5b60200260200101516020015190506000600281111561024f5761024e61157d565b5b8160028111156102625761026161157d565b5b036102765761027182846103c4565b61033c565b6001600281111561028a5761028961157d565b5b81600281111561029d5761029c61157d565b5b036102b1576102ac82846106e6565b61033b565b6002808111156102c4576102c361157d565b5b8160028111156102d7576102d661157d565b5b036102eb576102e68284610a26565b61033a565b8060028111156102fe576102fd61157d565b5b6040517f7fe9a41e00000000000000000000000000000000000000000000000000000000815260040161033191906115c8565b60405180910390fd5b5b5b5050508080600101915050610183565b507f8faa70878671ccd212d20771b795c50af8fd3ff6cf27f4bde57e5d4de0aeb67383838360405161038093929190611889565b60405180910390a16103928282610ea7565b505050565b6000807fc8fcad8db84d3cc18b4c41d551ea0ee66dd599cde068d998e57d5e09332c131c90508091505090565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160361043557806040517f0ae3681c00000000000000000000000000000000000000000000000000000000815260040161042c919061193d565b60405180910390fd5b600061043f610397565b905060008160010180549050905061046f84604051806060016040528060248152602001611b5b60249139610fc9565b60005b83518110156106df5760008482815181106104905761048f611533565b5b602002602001015190506000846000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161461058657816040517febbf5d0700000000000000000000000000000000000000000000000000000000815260040161057d919061196e565b60405180910390fd5b60405180604001604052808873ffffffffffffffffffffffffffffffffffffffff1681526020018561ffff16815250856000016000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160000160146101000a81548161ffff021916908361ffff160217905550905050846001018290806001815401808255809150506001900390600052602060002090600891828204019190066004029091909190916101000a81548163ffffffff021916908360e01c021790555083806106cd906119c6565b94505050508080600101915050610472565b5050505050565b60006106f0610397565b9050600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff160361076357816040517fcd98a96f00000000000000000000000000000000000000000000000000000000815260040161075a919061193d565b60405180910390fd5b61078583604051806060016040528060288152602001611ba760289139610fc9565b60005b8251811015610a205760008382815181106107a6576107a5611533565b5b602002602001015190506000836000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690503073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361089b57816040517f520300da000000000000000000000000000000000000000000000000000000008152600401610892919061196e565b60405180910390fd5b8573ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361090b57816040517f358d9d1a000000000000000000000000000000000000000000000000000000008152600401610902919061196e565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361097c57816040517f7479f939000000000000000000000000000000000000000000000000000000008152600401610973919061196e565b60405180910390fd5b85846000016000847bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050508080600101915050610788565b50505050565b6000610a30610397565b9050600081600101805490509050600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614610aaf57836040517fd091bc81000000000000000000000000000000000000000000000000000000008152600401610aa69190611562565b60405180910390fd5b60005b8351811015610ea0576000848281518110610ad057610acf611533565b5b602002602001015190506000846000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191681526020019081526020016000206040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016000820160149054906101000a900461ffff1661ffff1661ffff16815250509050600073ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff1603610c2757816040517f7a08a22d000000000000000000000000000000000000000000000000000000008152600401610c1e919061196e565b60405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff1603610c9b57816040517f6fafeb08000000000000000000000000000000000000000000000000000000008152600401610c92919061196e565b60405180910390fd5b8380610ca6906119fa565b94505083816020015161ffff1614610dbb576000856001018581548110610cd057610ccf611533565b5b90600052602060002090600891828204019190066004029054906101000a900460e01b90508086600101836020015161ffff1681548110610d1457610d13611533565b5b90600052602060002090600891828204019190066004026101000a81548163ffffffff021916908360e01c02179055508160200151866000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200190815260200160002060000160146101000a81548161ffff021916908361ffff160217905550505b84600101805480610dcf57610dce611a23565b5b60019003818190600052602060002090600891828204019190066004026101000a81549063ffffffff02191690559055846000016000837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19168152602001908152602001600020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556000820160146101000a81549061ffff0219169055505050508080600101915050610ab2565b5050505050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160315610fc557610efe82604051806060016040528060288152602001611b7f60289139610fc9565b6000808373ffffffffffffffffffffffffffffffffffffffff1683604051610f269190611a8e565b600060405180830381855af49150503d8060008114610f61576040519150601f19603f3d011682016040523d82523d6000602084013e610f66565b606091505b509150915081610fc257600081511115610f835780518082602001fd5b83836040517f192105d7000000000000000000000000000000000000000000000000000000008152600401610fb9929190611aa5565b60405180910390fd5b50505b5050565b6000823b9050600081036110165782826040517f919834b900000000000000000000000000000000000000000000000000000000815260040161100d929190611b2a565b60405180910390fd5b505050565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b60008083601f8401126110545761105361102f565b5b8235905067ffffffffffffffff81111561107157611070611034565b5b60208301915083602082028301111561108d5761108c611039565b5b9250929050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006110bf82611094565b9050919050565b6110cf816110b4565b81146110da57600080fd5b50565b6000813590506110ec816110c6565b92915050565b60008083601f8401126111085761110761102f565b5b8235905067ffffffffffffffff81111561112557611124611034565b5b60208301915083600182028301111561114157611140611039565b5b9250929050565b60008060008060006060868803121561116457611163611025565b5b600086013567ffffffffffffffff8111156111825761118161102a565b5b61118e8882890161103e565b955095505060206111a1888289016110dd565b935050604086013567ffffffffffffffff8111156111c2576111c161102a565b5b6111ce888289016110f2565b92509250509295509295909350565b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b611226826111dd565b810181811067ffffffffffffffff82111715611245576112446111ee565b5b80604052505050565b600061125861101b565b9050611264828261121d565b919050565b600067ffffffffffffffff821115611284576112836111ee565b5b602082029050602081019050919050565b600080fd5b600080fd5b600381106112ac57600080fd5b50565b6000813590506112be8161129f565b92915050565b600067ffffffffffffffff8211156112df576112de6111ee565b5b602082029050602081019050919050565b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b611325816112f0565b811461133057600080fd5b50565b6000813590506113428161131c565b92915050565b600061135b611356846112c4565b61124e565b9050808382526020820190506020840283018581111561137e5761137d611039565b5b835b818110156113a757806113938882611333565b845260208401935050602081019050611380565b5050509392505050565b600082601f8301126113c6576113c561102f565b5b81356113d6848260208601611348565b91505092915050565b6000606082840312156113f5576113f4611295565b5b6113ff606061124e565b9050600061140f848285016110dd565b6000830152506020611423848285016112af565b602083015250604082013567ffffffffffffffff8111156114475761144661129a565b5b611453848285016113b1565b60408301525092915050565b600061147261146d84611269565b61124e565b9050808382526020820190506020840283018581111561149557611494611039565b5b835b818110156114dc57803567ffffffffffffffff8111156114ba576114b961102f565b5b8086016114c789826113df565b85526020850194505050602081019050611497565b5050509392505050565b60006114f336848461145f565b905092915050565b611504816110b4565b82525050565b600060408201905061151f60008301856114fb565b61152c60208301846114fb565b9392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600060208201905061157760008301846114fb565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b600060ff82169050919050565b6115c2816115ac565b82525050565b60006020820190506115dd60008301846115b9565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b611618816110b4565b82525050565b6003811061162f5761162e61157d565b5b50565b60008190506116408261161e565b919050565b600061165082611632565b9050919050565b61166081611645565b82525050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b61169b816112f0565b82525050565b60006116ad8383611692565b60208301905092915050565b6000602082019050919050565b60006116d182611666565b6116db8185611671565b93506116e683611682565b8060005b838110156117175781516116fe88826116a1565b9750611709836116b9565b9250506001810190506116ea565b5085935050505092915050565b600060608301600083015161173c600086018261160f565b50602083015161174f6020860182611657565b506040830151848203604086015261176782826116c6565b9150508091505092915050565b60006117808383611724565b905092915050565b6000602082019050919050565b60006117a0826115e3565b6117aa81856115ee565b9350836020820285016117bc856115ff565b8060005b858110156117f857848403895281516117d98582611774565b94506117e483611788565b925060208a019950506001810190506117c0565b50829750879550505050505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015611844578082015181840152602081019050611829565b60008484015250505050565b600061185b8261180a565b6118658185611815565b9350611875818560208601611826565b61187e816111dd565b840191505092915050565b600060608201905081810360008301526118a38186611795565b90506118b260208301856114fb565b81810360408301526118c48184611850565b9050949350505050565b600082825260208201905092915050565b60006118ea82611666565b6118f481856118ce565b93506118ff83611682565b8060005b8381101561193057815161191788826116a1565b9750611922836116b9565b925050600181019050611903565b5085935050505092915050565b6000602082019050818103600083015261195781846118df565b905092915050565b611968816112f0565b82525050565b6000602082019050611983600083018461195f565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061ffff82169050919050565b60006119d1826119b8565b915061ffff82036119e5576119e4611989565b5b600182019050919050565b6000819050919050565b6000611a05826119f0565b915060008203611a1857611a17611989565b5b600182039050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603160045260246000fd5b600081905092915050565b6000611a688261180a565b611a728185611a52565b9350611a82818560208601611826565b80840191505092915050565b6000611a9a8284611a5d565b915081905092915050565b6000604082019050611aba60008301856114fb565b8181036020830152611acc8184611850565b90509392505050565b600081519050919050565b600082825260208201905092915050565b6000611afc82611ad5565b611b068185611ae0565b9350611b16818560208601611826565b611b1f816111dd565b840191505092915050565b6000604082019050611b3f60008301856114fb565b8181036020830152611b518184611af1565b9050939250505056fe4c69624469616d6f6e644375743a2041646420666163657420686173206e6f20636f64654c69624469616d6f6e644375743a205f696e6974206164647265737320686173206e6f20636f64654c69624469616d6f6e644375743a205265706c61636520666163657420686173206e6f20636f6465a2646970667358221220e1f9c026b96398b9505b153218f3e317de724387535a9b8fe951e5887a6abea464736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_DIAMONDCUT = "diamondCut";

    public static final Event DIAMONDCUT1_EVENT = new Event("DiamondCut", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<FacetCut>>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event DIAMONDCUT_EVENT = new Event("DiamondCut", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<FacetCut>>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected DiamondCutFacet(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DiamondCutFacet(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DiamondCutFacet(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DiamondCutFacet(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DiamondCut1EventResponse> getDiamondCut1Events(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DIAMONDCUT1_EVENT, transactionReceipt);
        ArrayList<DiamondCut1EventResponse> responses = new ArrayList<DiamondCut1EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DiamondCut1EventResponse typedResponse = new DiamondCut1EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._diamondCut = (List<FacetCut>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._init = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._calldata = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DiamondCut1EventResponse getDiamondCut1EventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIAMONDCUT1_EVENT, log);
        DiamondCut1EventResponse typedResponse = new DiamondCut1EventResponse();
        typedResponse.log = log;
        typedResponse._diamondCut = (List<FacetCut>) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse._init = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse._calldata = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<DiamondCut1EventResponse> diamondCut1EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiamondCut1EventFromLog(log));
    }

    public Flowable<DiamondCut1EventResponse> diamondCut1EventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIAMONDCUT1_EVENT));
        return diamondCut1EventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> diamondCut(List<FacetCut> _diamondCut,
            String _init, byte[] _calldata) {
        final Function function = new Function(
                FUNC_DIAMONDCUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<FacetCut>(FacetCut.class, _diamondCut), 
                new org.web3j.abi.datatypes.Address(160, _init), 
                new org.web3j.abi.datatypes.DynamicBytes(_calldata)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static DiamondCutFacet load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new DiamondCutFacet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DiamondCutFacet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DiamondCutFacet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DiamondCutFacet load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new DiamondCutFacet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DiamondCutFacet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DiamondCutFacet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DiamondCutFacet> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DiamondCutFacet.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DiamondCutFacet> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DiamondCutFacet.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<DiamondCutFacet> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DiamondCutFacet.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DiamondCutFacet> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DiamondCutFacet.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class DiamondCut1EventResponse extends BaseEventResponse {
        public List<FacetCut> _diamondCut;

        public String _init;

        public byte[] _calldata;
    }

    public static class DiamondCutEventResponse extends BaseEventResponse {
        public List<FacetCut> _diamondCut;

        public String _init;

        public byte[] _calldata;
    }
}
