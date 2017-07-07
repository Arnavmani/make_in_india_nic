/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function checkit() {
    var oldpwd = document.getElementById("oldPwd");
    var newpwd = document.getElementById("newPwd");
    var cnfrmpwd = document.getElementById("CnfrmPwd");

    getMD5(oldpwd);
    getMD5(newpwd);
    getMD5(cnfrmpwd);

    return true;
}

function getMD5(element) {
    var val = element.value;
    if (val !== "") {
        var md5val = hex_md5(val);
        element.value = md5val;
    }
}

function userCreatePasswordStrength(element)
{
    var password = element.value;
    var flag = true;
    if (password !== "" && !CheckPassword(password)) {
        alert("Invalid Password. Provide password as per the instructions");
        element.focus();
        element.value = "";
        flag = false;
    }
    return flag;
}

function CheckPassword(inputtxt)
{
    var decimal = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    return(inputtxt.match(decimal));
}

