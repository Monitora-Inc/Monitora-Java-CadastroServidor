import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class processId {

    public static String id(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        String idCpu = hal.getProcessor().getProcessorIdentifier().getProcessorID();
        return idCpu;
    }
}