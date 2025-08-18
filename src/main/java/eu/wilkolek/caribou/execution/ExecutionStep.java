package eu.wilkolek.caribou.execution;

import java.util.Set;

public class ExecutionStep {

    private final String name;
    private final Set<String> dependencies;
    private final String sql;


    private Boolean completed = false;


    public ExecutionStep(String name, Set<String> dependencies, String sql) {
        this.name = name;
        this.dependencies = dependencies;
        this.sql = sql;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public String getSql() {
        return sql;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public String getName() {
        return name;
    }

}