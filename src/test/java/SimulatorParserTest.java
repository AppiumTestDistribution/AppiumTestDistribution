import org.testng.annotations.Test;

public class SimulatorParserTest {

    @Test
    public void parseSimulatorJson() {
        String version = "9.1.1";
        Float aFloat = Float.valueOf(version.substring(0, version.length() - 2));
        System.out.println(aFloat);
    }
}
