/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.InComune;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String porzione=txtPorzioni.getText().trim();
    	if(porzione.length()==0) {
    		txtResult.appendText("Inserire un valore!");
    		return;
    	} else {
    		if(!porzione.matches("[0-9]")) {
    			txtResult.appendText("Carattere non corretto: Inserire un valore numerico!\n");
    			return;
    		}
    	}
    	int porz=Integer.parseInt(porzione);
    	this.model.creagrafo(porz);
    	
    	txtResult.appendText("Grafo creato ha " + this.model.getnvertici()+ " vertici e " + this.model.getnarchi()+ " archi\n");
    	
    	boxFood.getItems().addAll(this.model.getvertici());
    	txtResult.appendText("numero vertici nel box: "+ this.model.getvertici().size());

    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	
    	if(boxFood.getValue()==null) {
    		txtResult.appendText("Selezionare un valore!\n");
    		return;
    		}
    	Food ciboscelto= boxFood.getValue();
    	txtResult.appendText("Cibi con calorie maggiori per " + ciboscelto+ " sono: \n");
    	
    	List<InComune> caloriemax=this.model.getcaloriemax(ciboscelto);
    	int i=0;
    	Food ciborisultato=null;
		for( InComune c: caloriemax) {
			while(i<5) {
				ciborisultato=this.model.prendivicino(ciboscelto, c);
				if(ciborisultato!=null) {
					txtResult.appendText(i+") " + ciborisultato.toString());
	    			i++;
				}
			}
    	}
     }
    	

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
