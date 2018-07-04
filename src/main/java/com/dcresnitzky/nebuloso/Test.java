package main.java.com.dcresnitzky.nebuloso;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JDialogFis;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class Test {

    public static void main(String[] args) throws Exception {

        FIS fis = FIS.load("src/main/resources/fcl/company.fcl", true);
        // Create a plot
        JDialogFis jdf = null;
        if (!JFuzzyChart.UseMockClass) {
            jdf = new JDialogFis(fis, 1280, 720);
        }

        fis.getVariable("MarOper").setValue(5);
        fis.getVariable("ROE").setValue(9);
        fis.getVariable("ROI").setValue(3);
        fis.getVariable("MarLiq").setValue(3);
        fis.evaluate();

        System.out.println(String.format("Avaliacao: %2.2f %%", fis.getVariable("Avaliacao").getValue()));


        jdf.repaint();
    }
}
