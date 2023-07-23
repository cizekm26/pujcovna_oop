package cz.upce.fei.boop.pujcovna.gui;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

class ControlPanelHBox extends HBox {

    public ControlPanelHBox() {
        setPadding(new Insets(10, 20, 10, 10));
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
    }

    public void pridejComboBox(String text, Enum[] typy, EventHandler<ActionEvent> handler) {
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(Arrays.asList(typy)));
        comboBox.setPromptText(text);
        comboBox.setOnAction(handler);
        getChildren().add(comboBox);
    }
    
    public void pridejLabel(String text){
        Label label = new Label(text);
        getChildren().add(label);
    }
    
    public void pridejButton(String text, EventHandler<ActionEvent> handler){
        Button button = new Button(text);
        button.setOnAction(handler);
        button.setMinWidth(80);
        getChildren().add(button);
    } 
}
