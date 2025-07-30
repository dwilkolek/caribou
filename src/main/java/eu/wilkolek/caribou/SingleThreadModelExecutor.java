package eu.wilkolek.caribou;

class SingleThreadModelExecutor {

    private ExecutionPlan plan;

    public SingleThreadModelExecutor(ExecutionPlan plan) {
        this.plan = plan;
    }

    void execute() {
        while (plan.hasNext()) {
            var next = plan.getNext();
            next.materialize();
            plan.complete(next);
        }
    };
}