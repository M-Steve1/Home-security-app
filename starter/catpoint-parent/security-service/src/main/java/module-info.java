module com.stephen.securityservice {
    requires java.desktop;
    requires miglayout;
    opens com.stephen.securityservice.data to com.google.gson;
    requires guava.r05;
    requires java.prefs;
    requires com.google.gson;
    requires com.stephen.imageservice;
}