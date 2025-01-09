// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.2;

contract Storage{

    address private owner;
    uint256 private number;

    constructor(){
        owner=msg.sender;
        number=0;
    }
    
    modifier isOwner(){
        require(msg.sender==owner,"only owner can change the ownership");
        _;
    }


    function getOwner()public view returns(address){
        return owner;
    }

    function changeOwner(address _owner) public isOwner{
        owner=_owner;
    }
    function getNumber()public view returns(uint256){
        return number;
    }
    function setNumber(uint256 _number)public isOwner{
        number=_number;
    }



}