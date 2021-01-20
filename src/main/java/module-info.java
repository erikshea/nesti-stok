module com.nesti.stock_manager {
    requires java.desktop;
    requires mysql.connector.java;
    requires hibernate.entitymanager;
    exports com.nesti.stock_manager.application;
}
