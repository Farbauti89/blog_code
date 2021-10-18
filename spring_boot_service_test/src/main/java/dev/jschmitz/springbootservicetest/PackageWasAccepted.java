package dev.jschmitz.springbootservicetest;

import org.springframework.context.ApplicationEvent;

public class PackageWasAccepted extends ApplicationEvent {

    private final Package p;

    public PackageWasAccepted(Object source, Package p) {
        super(source);
        this.p = p;
    }

    public Package getPackage() {
        return p;
    }
}
