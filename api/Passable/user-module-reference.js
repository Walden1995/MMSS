//javascript equivalent of sample classes in PassableModule and PassableUSer

function get_user_reference(){
    var user = {};
    user["name"] = "John Doe";
    user["type"] = "guardian";
    user["id"] = "12345abcde";
    return user;
}

function get_module_reference(){
    var module = {
        name:"front door sensor",
        type:"sensormodule",
        id:"12345abcde",
        hubServerID:"10.0.0.5:3030",
        mainServerID:"123.456.789:8080",
        parameterData:[0, "test string", 'c', 50]
    };
    return module;
}

var myUser = get_user_reference();
console.log(JSON.stringify(myUser));

var myModule = get_module_reference();
console.log(JSON.stringify(myModule));
