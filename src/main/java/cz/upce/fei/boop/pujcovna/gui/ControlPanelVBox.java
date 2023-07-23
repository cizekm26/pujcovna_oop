package cz.upce.fei.boop.pujcovna.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

class ControlPanelVBox extends VBox{
    public ControlPanelVBox(){
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
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
