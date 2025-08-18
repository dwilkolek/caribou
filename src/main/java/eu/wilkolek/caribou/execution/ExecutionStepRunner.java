package eu.wilkolek.caribou.execution;

public interface ExecutionStepRunner {
    void execute(ExecutionStep step);
    void shutdown();
}
