package org.knowbase;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

/**
 * Created on 7/13/18 by alexiy.
 */
public class Alert2 extends Alert {


    public Alert2(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, "", buttons);
        setHeaderText(contentText);
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        setResizable(true);
    }
}
