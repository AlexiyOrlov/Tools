package dev.buildtool.tools.javafx;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Created on 7/21/18 by alexiy.
 */
public class Vbox2 extends VBox {
    public Vbox2() {
        super(6);
    }

    public Vbox2(Node... children) {
        super(6,children);
    }
}
