// SPDX-License-Identifier: MIT
pragma solidity ^0.8.2;

library LibERC20{
    bytes32 constant DIAMOND_ERC20_STORAGE_POSITION = keccak256("diamond.standard.erc20.storage");


    struct Token{
        string name;
        string symbol;
        uint8 decimals;
        uint256 totalSupply;
        mapping(address account => uint256) balances;
        mapping(address account => mapping(address spender => uint256)) allowances;
    }

    struct ERC20 {
        mapping(bytes32 => Token) tokens; // Mapping of token IDs to Token
        mapping(bytes32 => bool) isUsed; // Tracks whether a tokenId is already used
        mapping(bytes32 => address) tokenOwner; // Tracks owner of the token
    }

    /**
     * @dev Emitted when `value` tokens are moved from one account (`from`) to
     * another (`to`).
     *
     * Note that `value` may be zero.
     */
    event Transfer(address indexed from, address indexed to, uint256 value, bytes32 tokenID);

    /**
     * @dev Emitted when the allowance of a `spender` for an `owner` is set by
     * a call to {approve}. `value` is the new allowance.
     */
    event Approval(address indexed owner, address indexed spender, uint256 value, bytes32 tokenID);



    function diamondStorage() internal pure returns (ERC20 storage s) {
        bytes32 position = DIAMOND_ERC20_STORAGE_POSITION;
        assembly {
            s.slot := position
        }
    }

    
}