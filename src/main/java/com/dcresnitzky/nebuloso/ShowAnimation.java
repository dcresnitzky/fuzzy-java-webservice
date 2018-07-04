package main.java.com.dcresnitzky.nebuloso;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JDialogFis;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class ShowAnimation {

    private static Variable mOper;
    private static Variable roe;
    private static Variable roi;
    private static Variable mLiq;
    private static double steps = 150, mOperStep = 15/steps, roeStep = 46/steps, roiStep = 26/steps, mLiqStep = 10/steps;

    public static void main(String[] args) throws Exception {

        FIS fis = FIS.load("src/main/resources/fcl/company.fcl", true);

        JDialogFis jdf = null;
            if (!JFuzzyChart.UseMockClass) {
            jdf = new JDialogFis(fis, 1280, 720);
        }

        setVars(fis);

        int i;
        do {
            initVars();

            for (i = 0; i < steps -1; i++) {
                // Evaluate system using these parameters
                evaluate(fis);

                //Print result & update plot
                if (jdf != null) jdf.repaint();

                // Small delay
                Thread.sleep(100);
            }
        } while (true);

    }

    private static void setVars(FIS fis) {
        mOper = fis.getVariable("MarOper");
        roe = fis.getVariable("ROE");
        roi = fis.getVariable("ROI");
        mLiq = fis.getVariable("MarLiq");
    }

    private static void initVars() {
        mOper.setValue(-4.0);
        roe.setValue(-6.0);
        roi.setValue(-6.0);
        mLiq.setValue(-2.0);
    }

    private static void evaluate(FIS fis) {
        mOper.setValue(mOper.getValue() + mOperStep);
        roe.setValue(roe.getValue() + roeStep);
        roi.setValue(roi.getValue() + roiStep);
        mLiq.setValue(mLiq.getValue() + mLiqStep);
        fis.evaluate();
    }
}
