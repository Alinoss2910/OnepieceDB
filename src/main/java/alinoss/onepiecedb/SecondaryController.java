package alinoss.onepiecedb;

import alinoss.onepiecedb.entities.Barco;
import alinoss.onepiecedb.entities.Pirata;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class SecondaryController{
    private Pirata pirata;
    private static final String CARPETA_FOTOS = "Fotos";
    private boolean nuevoPirata;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldBanda;
    @FXML
    private TextField textFieldRol;
    @FXML
    private TextField textFieldRecom;
    @FXML
    private TextField textFieldFruta;
    @FXML
    private CheckBox checkBoxFruta;
    @FXML
    private TextField textFieldEdad;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private ComboBox<Barco> ComboBoxBarco;
    @FXML
    private BorderPane rootSecondary;
    
    public void setPirata(Pirata pirata, boolean nuevoPirata) {
        App.em.getTransaction().begin();
        if (!nuevoPirata){
            System.out.println(pirata.getId());
            System.out.println(pirata.getNombre());
            this.pirata = App.em.find(Pirata.class, pirata.getId());
        } else {
            this.pirata = pirata;
        }
        this.nuevoPirata = nuevoPirata;

        mostrarDatos();
    }
    
    private void mostrarDatos() {
        textFieldNombre.setText(pirata.getNombre());
        textFieldBanda.setText(pirata.getBanda());
        textFieldFruta.setText(pirata.getNombreFruta());
        textFieldRol.setText(pirata.getRol());
        
        if(pirata.getEdad() != null) {
            textFieldEdad.setText(String.valueOf(pirata.getEdad()));
        }
        if(pirata.getRecompensa()!= null) {
            textFieldRecom.setText(String.valueOf(pirata.getRecompensa()));
        }
        if(pirata.getFruta() != null) {
            checkBoxFruta.setSelected(pirata.getFruta());
        }
        
        Query queryBarcoFindAll = App.em.createNamedQuery("Barco.findAll");
        List<Barco> listBarco = queryBarcoFindAll.getResultList();
        
        ComboBoxBarco.setItems(FXCollections.observableList(listBarco));
        if(pirata.getBarco() != null) {
            ComboBoxBarco.setValue(pirata.getBarco());
        }
        ComboBoxBarco.setCellFactory((ListView<Barco> l) -> new ListCell<Barco>() {
            @Override
            protected void updateItem(Barco barco, boolean empty) {
                super.updateItem(barco, empty);
                if(barco == null || empty) {
                    setText("");
                }else {
                    setText(barco.getCodigo() + "-" + barco.getNombre());
                }
            }
        });
        ComboBoxBarco.setConverter(new StringConverter<Barco>() {
            @Override
            public String toString(Barco barco) {
                if(barco == null) {
                    return null;
                }else{
                    return barco.getCodigo() + "-" + barco.getNombre();
                }
            }
            
            @Override
            public Barco fromString(String userID) {
                return null;
            }
        });
        
        if(pirata.getFoto() != null) {
            String imageFileName = pirata.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if(file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        boolean errorFormato = false;
        
        pirata.setNombre(textFieldNombre.getText());
        pirata.setBanda(textFieldBanda.getText());
        pirata.setNombreFruta(textFieldFruta.getText());
        pirata.setRol(textFieldRol.getText());
        
        pirata.setFruta(checkBoxFruta.isSelected());
        
        pirata.setBarco(ComboBoxBarco.getValue());
        
        if(!textFieldEdad.getText().isEmpty()) {
            try {
                pirata.setEdad(Integer.valueOf(textFieldEdad.getText()));
            }catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Número de hijos no valido");
                alert.showAndWait();
                textFieldEdad.requestFocus();
            }
        }
        
        if(!textFieldRecom.getText().isEmpty()) {
            try {
                pirata.setRecompensa(BigInteger.valueOf(Long.valueOf(textFieldRecom.getText())));
            }catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Recompensa no valida");
                alert.showAndWait();
                textFieldEdad.requestFocus();
            }
        }
        
        if(!errorFormato) {
            try{
                if(pirata.getId() == null) {
                    System.out.println("Guardando nuevo pirata en BD");
                    App.em.persist(pirata);
                }else{
                    System.out.println("Actualizando pirata en BD");
                    App.em.merge(pirata);
                }
                App.em.getTransaction().commit();
                
                App.setRoot("primary");
            }catch(RollbackException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. " 
                    + "Compruebe que los datos cumplen con los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }catch(IOException ex) {
            
            }
        }
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        App.em.getTransaction().rollback();
        
        try{
            App.setRoot("primary");
        }catch(IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if(!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes (jpg, png)", "*.jpg", "*png"),
                new FileChooser.ExtensionFilter("Todos los Archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
        if(file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                pirata.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            }catch(FileAlreadyExistsException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre de archivo duplicado");
                alert.showAndWait();
            }catch(IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresion de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen, \n"
        + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operacion?");
        alert.setContentText("Elija la opcion deseada");
        
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancelar);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeEliminar) {
            String imageFileName = pirata.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if(file.exists()) {
                file.delete();
            }
            pirata.setFoto(null);
            imageViewFoto.setImage(null);
        }else if(result.get() == buttonTypeMantener) {
            pirata.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }
}