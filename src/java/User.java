
import impl.Database;
import Dobjdetails.Dobjdetails;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sourabh
 */
@ManagedBean
@ViewScoped
public class User {

    Dobjdetails dobje = new Dobjdetails();
    Database impl = new Database();

    /**
     * @return the dobj
     */
    public void adddata(){
        impl.hello1(dobje);
    }

    public Dobjdetails getDobje() {
        return dobje;
    }

    public void setDobje(Dobjdetails dobje) {
        this.dobje = dobje;
    }
    
    
    
    

}

