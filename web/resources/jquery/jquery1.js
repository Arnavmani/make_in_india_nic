/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
alert("hello");
function makePassword() {
    var element = document.getElementById("passId");
    var val = element.value;
    var regexforpass = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    alert("val======" + val);
    if (val !== null || val !== '') {
        if (val.match(regexforpass))
        {
            alert("byee");
            var md5val = hex_md5(val);
            var element1 = document.getElementById('hidden_field');
            var randomnumber = element1.value;
            val = md5val + randomnumber;
            element.value = hex_md5(val);
            element1.value = randomnumber;
            return true;
        }
        else
        {
            alert("Password should contain\n\
// 1.At least one upper case english letter\n\
2.At least one lower case english letter\n\
3.At least one digit\n\
4.At least one special character\n\
5.Minimum 8 in length");

        }
    }




}


