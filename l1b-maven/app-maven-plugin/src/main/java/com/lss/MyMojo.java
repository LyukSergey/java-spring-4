package com.lss;

import org.apache.maven.plugin.AbstractMojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "my-goal", defaultPhase = LifecyclePhase.COMPILE)
public class MyMojo extends AbstractMojo {

    @Parameter(property = "message", defaultValue = "Hello!")
    private String message;

    public void execute() {
        getLog().info("My custom plugin says: " + message);
    }
}
