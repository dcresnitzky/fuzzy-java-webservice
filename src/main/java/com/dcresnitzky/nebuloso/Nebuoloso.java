package main.java.com.dcresnitzky.nebuloso;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import org.antlr.runtime.RecognitionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Path("/")
public class Nebuoloso {

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("application/json")
    public String getClichedMessage(@QueryParam("ope") double ope,
                                    @QueryParam("liq") double liq,
                                    @QueryParam("roe") double roe,
                                    @QueryParam("roi") double roi) {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("main/resources/fcl/company.fcl");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String fcl = reader.lines().collect(Collectors.joining("\n"));

        FIS fis = null;

        try {
            fis = FIS.createFromString(fcl, true);
        } catch (RecognitionException e) {
            e.printStackTrace();
        }


        FunctionBlock functionBlock = null;
        if (fis != null) {
            functionBlock = fis.getFunctionBlock(null);

            // Set inputs
            functionBlock.setVariable("MarOper", ope);
            functionBlock.setVariable("MarLiq", liq);
            functionBlock.setVariable("ROE", roe);
            functionBlock.setVariable("ROI", roi);

            // Evaluate
            functionBlock.evaluate();

            return "" + functionBlock.getVariable("Avaliacao").getValue();
        }
        return "error";
    }
}