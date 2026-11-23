module easvprojects.mytunes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;                                      // added: needed for MediaPlayer

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jdk.compiler;

    opens easvprojects.mytunes to javafx.fxml;
    opens easvprojects.mytunes.gui.controller to javafx.fxml;   // added: fixes your crash
    opens easvprojects.mytunes.be to javafx.base;
    opens easvprojects.mytunes.dal.db;   // lets JUnit create the DAO test classes when IntelliJ runs them
    opens easvprojects.mytunes.bll;      // same, for the manager test classes
    exports easvprojects.mytunes;
}