/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class AutoCompleteComboBoxListener<T> {
    private final ComboBox<T> comboBox;
    final private ObservableList<T> data;

    public AutoCompleteComboBoxListener(final ComboBox<T> comboBox) {
        this.comboBox = comboBox;
        this.data = FXCollections.observableArrayList(comboBox.getItems());

        this.comboBox.setOnKeyPressed(this::handleOnKeyPressed);
        this.comboBox.setOnHidden(this::handleOnHiding);
        this.doAutoCompleteBox();
    }

    AutoCompleteComboBoxListener() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doAutoCompleteBox() {
        this.comboBox.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.comboBox.show();
            }
        });

        this.comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            moveCaret(this.comboBox.getEditor().getText().length());
        });
    }

    public void handleOnHiding(Event event) {
        comboBox.setEditable(false);
        T temp = comboBox.getSelectionModel().getSelectedItem();
        comboBox.setValue(temp);
    }

    public void handleOnKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.UP || code == KeyCode.DOWN
                || code == KeyCode.RIGHT || code == KeyCode.LEFT
                || code == KeyCode.HOME
                || code == KeyCode.END || code == KeyCode.TAB
        ) {
            return;
        }
        String str = this.comboBox.getEditor().getText();
        if (code == KeyCode.INSERT) {
            comboBox.setEditable(true);
        }
        if (str.length() == 0) {
            this.comboBox.setItems(this.data);
        }
        if (code == KeyCode.ENTER)
            setItems();
    }

    private void setItems() {
        ObservableList<T> list = FXCollections.observableArrayList();

        Stream<T> items = comboBox.getItems().stream();
        String txtUsr = this.comboBox.getEditor().getText().toLowerCase();
        for (T item : this.data) {
            if (item.toString().toLowerCase().contains(txtUsr))
                list.add(item);
        }

        if (list.isEmpty()) this.comboBox.hide();

        this.comboBox.setItems(list);
        this.comboBox.show();
    }

    private void moveCaret(int textLength) {
        this.comboBox.getEditor().positionCaret(textLength);
    }   
}
