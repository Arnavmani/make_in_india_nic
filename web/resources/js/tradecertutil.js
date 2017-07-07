/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function cal(){
  var sum =0;
  for(var i=0; ;i++){
    if(document.getElementById("new_trade_cert_application_subview:application_sections_data_table_renew:"+i+":lb_no_of_allowed_vehicles_editable") !=null){
         var value =document.getElementById("new_trade_cert_application_subview:application_sections_data_table_renew:"+i+":lb_no_of_allowed_vehicles_editable").value.trim();
           if(value =="" ){
              value=0;
              document.getElementById("new_trade_cert_application_subview:application_sections_data_table_renew:"+i+":lb_no_of_allowed_vehicles_editable").value = 0;
          }
        sum += parseInt(value);
    }else{
          break;
    }
  }                   
  document.getElementById("new_trade_cert_application_subview:application_sections_data_table_renew:tf_grand_total_renewed").value = sum;
}