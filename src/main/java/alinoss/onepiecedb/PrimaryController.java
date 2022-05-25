package alinoss.onepiecedb;

import alinoss.onepiecedb.entities.Pirata;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

public class PrimaryController implements Initializable{
    
    private Pirata pirataSeleccionado;
    @FXML
    private TableView<Pirata> tableViewPirata;
    @FXML
    private TableColumn<Pirata, String> columnNombre;
    @FXML
    private TableColumn<Pirata, String> columnBanda;
    @FXML
    private TableColumn<Pirata, String> columnFruta;
    @FXML
    private TableColumn<Pirata, String> columnBarco;
    @FXML
    private TextField textP;
    @FXML
    private TextField textB;
    @FXML
    private TableColumn<Pirata, String> columnRecom;
    @FXML
    private CheckBox checkExacto;
    @FXML
    private TextField textFieldBuscar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnBanda.setCellValueFactory(new PropertyValueFactory<>("banda"));         
        columnFruta.setCellValueFactory(new PropertyValueFactory<>("nombreFruta")); 
        columnRecom.setCellValueFactory(new PropertyValueFactory<>("recompensa"));
        
        columnBarco.setCellValueFactory(                 
                cellData -> {                     
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getBarco()!= null) {
                        property.setValue(cellData.getValue().getBarco().getNombre());                     
                    }                     
                    return property;
                });
        tableViewPirata.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    pirataSeleccionado = newValue;
                        if (pirataSeleccionado != null){
                            textP.setText(pirataSeleccionado.getNombre());
                            textB.setText(pirataSeleccionado.getBarco().getNombre());
                        }else{
                            textP.setText("");
                            textB.setText("");
                        }
                });
        cargarTodosPiratas();
    }
    @FXML
    private void onActionGuardar(ActionEvent event) {
        if (pirataSeleccionado != null){
            pirataSeleccionado.setNombre(textP.getText());
            pirataSeleccionado.getBarco().setNombre(textB.getText());
            App.em.getTransaction().begin();
            App.em.merge(pirataSeleccionado);
            App.em.getTransaction().commit();
            
            int numFilaSeleccionada = tableViewPirata.getSelectionModel().getSelectedIndex();
            tableViewPirata.getItems().set(numFilaSeleccionada, pirataSeleccionado);
            TablePosition pos = new TablePosition(tableViewPirata, numFilaSeleccionada, null);
            tableViewPirata.getFocusModel().focus(pos);
            tableViewPirata.requestFocus();
        }    
    }
    
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    private void cargarTodosPiratas(){
        Query queryPirataFindAll = App.em.createNamedQuery("Pirata.findAll");
        List<Pirata> listPirata = queryPirataFindAll.getResultList();
        tableViewPirata.setItems(FXCollections.observableArrayList(listPirata));
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        if(pirataSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Desea suprimir el siguiente registro?");
            alert.setContentText(pirataSeleccionado.getNombre());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                App.em.getTransaction().begin();
                App.em.remove(pirataSeleccionado);
                App.em.getTransaction().commit();
                tableViewPirata.getItems().remove(pirataSeleccionado);
                tableViewPirata.getFocusModel().focus(null);
                tableViewPirata.requestFocus();
            }else{
                int numFilaSelecionada = tableViewPirata.getSelectionModel().getSelectedIndex();
                tableViewPirata.getItems().set(numFilaSelecionada, pirataSeleccionado);
                TablePosition pos = new TablePosition(tableViewPirata, numFilaSelecionada, null);
                tableViewPirata.getFocusModel().focus(pos);
                tableViewPirata.requestFocus();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try {
            App.setRoot("secondary");
            SecondaryController secondary = (SecondaryController)App.fxmlLoader.getController();
            pirataSeleccionado = new Pirata();
            secondary.setPirata(pirataSeleccionado, true);
        }catch(IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        if(pirataSeleccionado != null) {
            try {
                App.setRoot("secondary");
                SecondaryController secondary = (SecondaryController)App.fxmlLoader.getController();
                secondary.setPirata(pirataSeleccionado, false);
            }catch(IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionButtonBuscar(ActionEvent event) {
        if(!textFieldBuscar.getText().isEmpty()) {
            if(checkExacto.isSelected()) {
                Query queryPirataByNombre = App.em.createNamedQuery("Pirata.findByNombre");
                queryPirataByNombre.setParameter("nombre", textFieldBuscar.getText());
                List<Pirata> listPirata = queryPirataByNombre.getResultList();
                tableViewPirata.setItems(FXCollections.observableArrayList(listPirata));
            }else{
                String strQuery = "SELECT * FROM Pirata WHERE LOWER(nombre) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryPirataByNombre = App.em.createNativeQuery(strQuery, Pirata.class);
                
                List<Pirata> listPirata = queryPirataByNombre.getResultList();
                tableViewPirata.setItems(FXCollections.observableArrayList(listPirata));
            }
        }else{
            cargarTodosPiratas();
        }
    }
}
