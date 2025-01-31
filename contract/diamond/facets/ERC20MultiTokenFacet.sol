// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;


import {Context} from "../../utils/Context.sol";
import {IERC20Errors} from "../../utils/IERC20Errors.sol";


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

contract ERC20MultiTokenFacet is Context,IERC20Errors{

    

    /**
     * @dev Returns the name of the token.
     */
    function name(bytes32 tokenID) external view  returns (string memory) {
        return LibERC20.diamondStorage().tokens[tokenID].name;
    }

    /**
     * @dev Returns the symbol of the token, usually a shorter version of the
     * name.
     */
    function symbol(bytes32 tokenID) external view  returns (string memory) {
        return LibERC20.diamondStorage().tokens[tokenID].symbol;

    }

    /**
     * @dev Returns the number of decimals used to get its user representation.
     * For example, if `decimals` equals `2`, a balance of `505` tokens should
     * be displayed to a user as `5.05` (`505 / 10 ** 2`).
     *
     * Tokens usually opt for a value of 18, imitating the relationship between
     * Ether and Wei. This is the default value returned by this function, unless
     * it's overridden.
     *
     * NOTE: This information is only used for _display_ purposes: it in
     * no way affects any of the arithmetic of the contract, including
     * {IERC20-balanceOf} and {IERC20-transfer}.
     */
    function decimals(bytes32 tokenID) external view returns (uint8) {
        return LibERC20.diamondStorage().tokens[tokenID].decimals;
    }

    /**
     * @dev See {IERC20-totalSupply}.
     */
    function totalSupply(bytes32 tokenID) external view  returns (uint256) {
        return LibERC20.diamondStorage().tokens[tokenID].totalSupply;
    }

    /**
     * @dev See {IERC20-balanceOf}.
     */
    function balanceOf(bytes32 tokenID,address account) external view  returns (uint256) {
        return LibERC20.diamondStorage().tokens[tokenID].balances[account];
    }

    /**
     * @dev See {IERC20-transfer}.
     *
     * Requirements:
     *
     * - `to` cannot be the zero address.
     * - the caller must have a balance of at least `value`.
     */
    function transfer(bytes32 tokenID,address to, uint256 value) external returns (bool) {
        address owner = _msgSender();
        _transfer(tokenID,owner, to, value);
        return true;
    }

    /**
     * @dev See {IERC20-allowance}.
     */
    function allowance(bytes32 _tokenID,address owner, address spender) external view returns (uint256) {
        return LibERC20.diamondStorage().tokens[_tokenID].allowances[owner][spender];
    }

    /**
     * @dev See {IERC20-approve}.
     *
     * NOTE: If `value` is the maximum `uint256`, the allowance is not updated on
     * `transferFrom`. This is semantically equivalent to an infinite approval.
     *
     * Requirements:
     *
     * - `spender` cannot be the zero address.
     */
    function approve(bytes32 tokenID,address spender, uint256 value) external returns (bool) {
        address owner = _msgSender();
        _approve(tokenID, owner, spender, value);
        return true;
    }

    /**
     * @dev See {IERC20-transferFrom}.
     *
     * Skips emitting an {Approval} event indicating an allowance update. This is not
     * required by the ERC. See {xref-ERC20-_approve-address-address-uint256-bool-}[_approve].
     *
     * NOTE: Does not update the allowance if the current allowance
     * is the maximum `uint256`.
     *
     * Requirements:
     *
     * - `from` and `to` cannot be the zero address.
     * - `from` must have a balance of at least `value`.
     * - the caller must have allowance for ``from``'s tokens of at least
     * `value`.
     */
    function transferFrom(bytes32 tokenID, address from, address to, uint256 value) external  returns (bool) {
        address spender = _msgSender();
        _spendAllowance(tokenID, from, spender, value);
        _transfer(tokenID,from, to, value);
        return true;
    }

    /**
     * @dev Moves a `value` amount of tokens from `from` to `to`.
     *
     * This internal function is equivalent to {transfer}, and can be used to
     * e.g. implement automatic token fees, slashing mechanisms, etc.
     *
     * Emits a {Transfer} event.
     *
     * NOTE: This function is not virtual, {_update} should be overridden instead.
     */
    function _transfer(bytes32 tokenID,address from, address to, uint256 value) internal {
        if (from == address(0)) {
            revert ERC20InvalidSender(address(0));
        }
        if (to == address(0)) {
            revert ERC20InvalidReceiver(address(0));
        }
        _update(tokenID,from, to, value);
    }

    /**
     * @dev Transfers a `value` amount of tokens from `from` to `to`, or alternatively mints (or burns) if `from`
     * (or `to`) is the zero address. All customizations to transfers, mints, and burns should be done by overriding
     * this function.
     *
     * Emits a {Transfer} event.
     */
    function _update(bytes32 tokenID,address from, address to, uint256 value) internal virtual {
        
        LibERC20.Token storage token=LibERC20.diamondStorage().tokens[tokenID];
        
        if (from == address(0)) {
            // Overflow check required: The rest of the code assumes that totalSupply never overflows
            token.totalSupply += value;
        } else {
            uint256 fromBalance = token.balances[from];
            if (fromBalance < value) {
                revert ERC20InsufficientBalance(from, fromBalance, value);
            }
            unchecked {
                // Overflow not possible: value <= fromBalance <= totalSupply.
                token.balances[from] = fromBalance - value;
            }
        }

        if (to == address(0)) {
            unchecked {
                // Overflow not possible: value <= totalSupply or value <= fromBalance <= totalSupply.
                token.totalSupply -= value;
            }
        } else {
            unchecked {
                // Overflow not possible: balance + value is at most totalSupply, which we know fits into a uint256.
                token.balances[to] += value;
            }
        }

        emit LibERC20.Transfer(from, to, value,tokenID);
    }

    function mint(bytes32 tokenID, address account, uint256 value, string memory _name, string memory _symbol, uint8 _decimals)external{
        _mint(tokenID,account,value,_name,_symbol,_decimals);
    }

    /**
     * @dev Creates a `value` amount of tokens and assigns them to `account`, by transferring it from address(0).
     * Relies on the `_update` mechanism
     *
     * Emits a {Transfer} event with `from` set to the zero address.
     *
     * NOTE: This function is not virtual, {_update} should be overridden instead.
     */
    function _mint(bytes32 tokenID, address account, uint256 value, string memory _name, string memory _symbol, uint8 _decimals) internal {

        LibERC20.ERC20 storage s=LibERC20.diamondStorage();        
        if(s.isUsed[tokenID]){
            revert ERC20TokenAlreadyMinted(tokenID);
        }

        LibERC20.Token storage token=s.tokens[tokenID];

        token.name=_name;
        token.symbol=_symbol;
        token.decimals=_decimals;
        s.tokenOwner[tokenID]=account;

        if (account == address(0)) {
            revert ERC20InvalidReceiver(address(0));
        }
        _update(tokenID, address(0), account, value);
    }


    function burn(bytes32 tokenID, address account, uint256 value) external {
        require(LibERC20.diamondStorage().tokenOwner[tokenID]==_msgSender(),"Only token owner can burn tokens");
        _burn(tokenID,account,value);
    }


    /**
     * @dev Destroys a `value` amount of tokens from `account`, lowering the total supply.
     * Relies on the `_update` mechanism.
     *
     * Emits a {Transfer} event with `to` set to the zero address.
     *
     * NOTE: This function is not virtual, {_update} should be overridden instead
     */
    function _burn(bytes32 tokenID, address account, uint256 value) internal {
        if (account == address(0)) {
            revert ERC20InvalidSender(address(0));
        }
        _update(tokenID, account, address(0), value);
    }

    /**
     * @dev Sets `value` as the allowance of `spender` over the `owner` s tokens.
     *
     * This internal function is equivalent to `approve`, and can be used to
     * e.g. set automatic allowances for certain subsystems, etc.
     *
     * Emits an {Approval} event.
     *
     * Requirements:
     *
     * - `owner` cannot be the zero address.
     * - `spender` cannot be the zero address.
     *
     * Overrides to this logic should be done to the variant with an additional `bool emitEvent` argument.
     */
    function _approve(bytes32 tokenID, address owner, address spender, uint256 value) internal {
        _approve(tokenID, owner, spender, value, true);
    }

    /**
     * @dev Variant of {_approve} with an optional flag to enable or disable the {Approval} event.
     *
     * By default (when calling {_approve}) the flag is set to true. On the other hand, approval changes made by
     * `_spendAllowance` during the `transferFrom` operation set the flag to false. This saves gas by not emitting any
     * `Approval` event during `transferFrom` operations.
     *
     * Anyone who wishes to continue emitting `Approval` events on the`transferFrom` operation can force the flag to
     * true using the following override:
     *
     * ```solidity
     * function _approve(address owner, address spender, uint256 value, bool) internal virtual override {
     *     super._approve(owner, spender, value, true);
     * }
     * ```
     *
     * Requirements are the same as {_approve}.
     */
    function _approve(bytes32 tokenID,address owner, address spender, uint256 value, bool emitEvent) internal virtual {
        LibERC20.ERC20 storage s=LibERC20.diamondStorage();
        LibERC20.Token storage token=s.tokens[tokenID];
        if (owner == address(0)) {
            revert ERC20InvalidApprover(address(0));
        }
        if (spender == address(0)) {
            revert ERC20InvalidSpender(address(0));
        }
        token.allowances[owner][spender] = value;
        if (emitEvent) {
            emit LibERC20.Approval(owner, spender, value,tokenID);
        }
    }

    /**
     * @dev Updates `owner` s allowance for `spender` based on spent `value`.
     *
     * Does not update the allowance value in case of infinite allowance.
     * Revert if not enough allowance is available.
     *
     * Does not emit an {Approval} event.
     */
    function _spendAllowance(bytes32 tokenID, address owner, address spender, uint256 value) internal virtual {
        uint256 currentAllowance = LibERC20.diamondStorage().tokens[tokenID].allowances[owner][spender];
        if (currentAllowance < type(uint256).max) {
            if (currentAllowance < value) {
                revert ERC20InsufficientAllowance(spender, currentAllowance, value);
            }
            unchecked {
                _approve(tokenID, owner, spender, currentAllowance - value, false);
            }
        }
    }



}