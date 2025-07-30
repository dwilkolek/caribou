package eu.wilkolek.caribou.materilization;

import eu.wilkolek.caribou.model.Model;

public class SnowflakeIncrementalMaterializer implements Materializer {

    @Override
    public void materialize(Model model) {
        System.out.println("BEGIN");
        System.out.println("INSERT OVERWRITE " + model.name + " " + model.compiledSql);
        System.out.println("COMMIT");
    }
}
