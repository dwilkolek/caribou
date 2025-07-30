package eu.wilkolek.caribou.materilization;

import eu.wilkolek.caribou.model.Model;

public class TableMaterializer implements Materializer {

    @Override
    public void materialize(Model model) {
        System.out.println("BEGIN");
        System.out.println("CREATE OR REPLACE TABLE " + model.name + " AS " + model.compiledSql);
        System.out.println("COMMIT");
    }
}
