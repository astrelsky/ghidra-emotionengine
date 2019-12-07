package ghidra.emotionengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ghidra.emotionengine.InjectPayloadVu;
import ghidra.emotionengine.PcodeInjectLibraryVu;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class InjectPayloadVuTest extends InjectPayloadVu {

    private static final Map<String, String> EXPECTED_RESULTS = getResultMap();
    private static final String ERROR_MESSAGE = "%s:\n%s\nReceived:\n%s";
    private static final int DEST = 13;

    public InjectPayloadVuTest() {
        this(null);
    }

    private InjectPayloadVuTest(String name) {
        super(null, null);
        this.name = name;
        this.dest = DEST;
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(InjectPayloadVuTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    @Test
    public void opcodeTest() throws Exception {
        InjectPayloadVuTest currentTest = new InjectPayloadVuTest(null);
        for (String opcode : PcodeInjectLibraryVu.getVectorInstructions()) {
            currentTest.name = opcode;
            currentTest.checkResults();
        }
    }

    private void checkResults() throws Exception {
        Function<InjectPayloadVu, String> function = getInstruction();
        String result = function.apply(this);
        assert result.equals(EXPECTED_RESULTS.get(name)) :
            String.format(ERROR_MESSAGE, name, EXPECTED_RESULTS.get(name), result);
    }

    private static Map<String, String> getResultMap() {
        Map<String, String> results = new HashMap<>();
        results.put(PcodeInjectLibraryVu.VABS, "VUFT[0,32] = abs(VUFS[0,32]);\nVUFT[32,32] = abs(VUFS[32,32]);\nVUFT[96,32] = abs(VUFS[96,32]);\n");
        results.put(PcodeInjectLibraryVu.VADD, "VUFD[0,32] = VUFS[0,32] f+ VUFT[0,32];\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = 0;\nvuMAC_32[15,1] = nan(VUFD[0,32]);\nVUFD[32,32] = VUFS[32,32] f+ VUFT[32,32];\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = 0;\nvuMAC_32[14,1] = nan(VUFD[32,32]);\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = VUFS[96,32] f+ VUFT[96,32];\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = 0;\nvuMAC_32[12,1] = nan(VUFD[96,32]);\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VADDBC, "VUFD[0,32] = VUFS[0,32] f+ VUFT;\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = 0;\nvuMAC_32[15,1] = nan(VUFD[0,32]);\nVUFD[32,32] = VUFS[32,32] f+ VUFT;\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = 0;\nvuMAC_32[14,1] = nan(VUFD[32,32]);\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = VUFS[96,32] f+ VUFT;\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = 0;\nvuMAC_32[12,1] = nan(VUFD[96,32]);\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VMADD, "VUFD[0,32] = vuACC[0,32] f+ VUFS[0,32] f* VUFT[0,32];\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = 0;\nvuMAC_32[15,1] = nan(VUFD[0,32]);\nVUFD[32,32] = vuACC[32,32] f+ VUFS[32,32] f* VUFT[32,32];\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = 0;\nvuMAC_32[14,1] = nan(VUFD[32,32]);\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = vuACC[96,32] f+ VUFS[96,32] f* VUFT[96,32];\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = 0;\nvuMAC_32[12,1] = nan(VUFD[96,32]);\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VMADDBC, "VUFD[0,32] = vuACC[0,32] f+ VUFS[0,32] f* VUFT;\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = 0;\nvuMAC_32[15,1] = nan(VUFD[0,32]);\nVUFD[32,32] = vuACC[32,32] f+ VUFS[32,32] f* VUFT;\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = 0;\nvuMAC_32[14,1] = nan(VUFD[32,32]);\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = vuACC[96,32] f+ VUFS[96,32] f* VUFT;\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = 0;\nvuMAC_32[12,1] = nan(VUFD[96,32]);\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VSUB, "VUFD[0,32] = VUFS[0,32] f- VUFT[0,32];\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = nan(VUFD[0,32]);\nvuMAC_32[15,1] = 0;\nVUFD[32,32] = VUFS[32,32] f- VUFT[32,32];\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = nan(VUFD[32,32]);\nvuMAC_32[14,1] = 0;\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = VUFS[96,32] f- VUFT[96,32];\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = nan(VUFD[96,32]);\nvuMAC_32[12,1] = 0;\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VSUBBC, "VUFD[0,32] = VUFS[0,32] f- VUFT;\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = nan(VUFD[0,32]);\nvuMAC_32[15,1] = 0;\nVUFD[32,32] = VUFS[32,32] f- VUFT;\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = nan(VUFD[32,32]);\nvuMAC_32[14,1] = 0;\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = VUFS[96,32] f- VUFT;\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = nan(VUFD[96,32]);\nvuMAC_32[12,1] = 0;\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VMSUB, "VUFD[0,32] = vuACC[0,32] f- VUFS[0,32] f* VUFT[0,32];\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = nan(VUFD[0,32]);\nvuMAC_32[15,1] = 0;\nVUFD[32,32] = vuACC[32,32] f- VUFS[32,32] f* VUFT[32,32];\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = nan(VUFD[32,32]);\nvuMAC_32[14,1] = 0;\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = vuACC[96,32] f- VUFS[96,32] f* VUFT[96,32];\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = nan(VUFD[96,32]);\nvuMAC_32[12,1] = 0;\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VMSUBBC, "VUFD[0,32] = vuACC[0,32] f- VUFS[0,32] f* VUFT;\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = nan(VUFD[0,32]);\nvuMAC_32[15,1] = 0;\nVUFD[32,32] = vuACC[32,32] f- VUFS[32,32] f* VUFT;\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = nan(VUFD[32,32]);\nvuMAC_32[14,1] = 0;\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = vuACC[96,32] f- VUFS[96,32] f* VUFT;\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = nan(VUFD[96,32]);\nvuMAC_32[12,1] = 0;\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VMUL, "VUFD[0,32] = VUFS[0,32] f* VUFT[0,32];\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = 0;\nvuMAC_32[15,1] = 0;\nVUFD[32,32] = VUFS[32,32] f* VUFT[32,32];\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = 0;\nvuMAC_32[14,1] = 0;\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = VUFS[96,32] f* VUFT[96,32];\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = 0;\nvuMAC_32[12,1] = 0;\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VMULBC, "VUFD[0,32] = VUFS[0,32] f* VUFT;\nvuMAC_32[3,1] = (VUFD[0,32] == 0);\nvuMAC_32[7,1] = (VUFD[0,32] < 0);\nvuMAC_32[11,1] = 0;\nvuMAC_32[15,1] = 0;\nVUFD[32,32] = VUFS[32,32] f* VUFT;\nvuMAC_32[2,1] = (VUFD[32,32] == 0);\nvuMAC_32[6,1] = (VUFD[32,32] < 0);\nvuMAC_32[10,1] = 0;\nvuMAC_32[14,1] = 0;\nvuMAC_32[1,1] = 0;\nvuMAC_32[5,1] = 0;\nvuMAC_32[9,1] = 0;\nvuMAC_32[13,1] = 0;\nVUFD[96,32] = VUFS[96,32] f* VUFT;\nvuMAC_32[0,1] = (VUFD[96,32] == 0);\nvuMAC_32[4,1] = (VUFD[96,32] < 0);\nvuMAC_32[8,1] = 0;\nvuMAC_32[12,1] = 0;\nvuStatus_32[0,1] = vuMAC_32[0,1] || vuMAC_32[1,1] || vuMAC_32[2,1] || vuMAC_32[3,1];\nvuStatus_32[1,1] = vuMAC_32[4,1] || vuMAC_32[5,1] || vuMAC_32[6,1] || vuMAC_32[7,1];\nvuStatus_32[2,1] = vuMAC_32[8,1] || vuMAC_32[9,1] || vuMAC_32[10,1] || vuMAC_32[11,1];\nvuStatus_32[3,1] = vuMAC_32[12,1] || vuMAC_32[13,1] || vuMAC_32[14,1] || vuMAC_32[15,1];\nvuStatus_32[6,1] = vuStatus_32[6,1] || vuStatus_32[0,1];\nvuStatus_32[7,1] = vuStatus_32[7,1] || vuStatus_32[1,1];\nvuStatus_32[8,1] = vuStatus_32[8,1] || vuStatus_32[2,1];\nvuStatus_32[9,1] = vuStatus_32[9,1] || vuStatus_32[3,1];\n");
        results.put(PcodeInjectLibraryVu.VFTOI, "VUFT[0,32] = trunc(VUFS[0,32]);\nVUFT[32,32] = trunc(VUFS[32,32]);\nVUFT[96,32] = trunc(VUFS[96,32]);\n");
        results.put(PcodeInjectLibraryVu.VITOF, "VUFT[0,32] = int2float(VUFS[0,32]);\nVUFT[32,32] = int2float(VUFS[32,32]);\nVUFT[96,32] = int2float(VUFS[96,32]);\n");
        results.put(PcodeInjectLibraryVu.VULQ, "VUFT[0,32] = *:4 (addr + 12);\nVUFT[32,32] = *:4 (addr + 8);\nVUFT[96,32] = *:4 (addr + 0);\n");
        results.put(PcodeInjectLibraryVu.VUSQ, "*:4 (addr + 12) = VUFS[0,32];\n*:4 (addr + 8) = VUFS[32,32];\n*:4 (addr + 0) = VUFS[96,32];\n");
        results.put(PcodeInjectLibraryVu.VMAX, "if (VUFS[0,32] f> VUFT[0,32]) goto <max3>;\nVUFD[0,32] = VUFT[0,32];\ngoto <end3>;\n<max3>\nVUFD[0,32] = VUFS[0,32];\n<end3>\nif (VUFS[32,32] f> VUFT[32,32]) goto <max2>;\nVUFD[32,32] = VUFT[32,32];\ngoto <end2>;\n<max2>\nVUFD[32,32] = VUFS[32,32];\n<end2>\nif (VUFS[96,32] f> VUFT[96,32]) goto <max0>;\nVUFD[96,32] = VUFT[96,32];\ngoto <end0>;\n<max0>\nVUFD[96,32] = VUFS[96,32];\n<end0>\n");
        results.put(PcodeInjectLibraryVu.VMAXBC, "if (VUFS[0,32] f> VUFT) goto <max3>;\nVUFD[0,32] = VUFT;\ngoto <end3>;\n<max3>\nVUFD[0,32] = VUFS[0,32];\n<end3>\nif (VUFS[32,32] f> VUFT) goto <max2>;\nVUFD[32,32] = VUFT;\ngoto <end2>;\n<max2>\nVUFD[32,32] = VUFS[32,32];\n<end2>\nif (VUFS[96,32] f> VUFT) goto <max0>;\nVUFD[96,32] = VUFT;\ngoto <end0>;\n<max0>\nVUFD[96,32] = VUFS[96,32];\n<end0>\n");
        results.put(PcodeInjectLibraryVu.VMIN, "if (VUFS[0,32] f< VUFT[0,32]) goto <min3>;\nVUFD[0,32] = VUFT[0,32];\ngoto <end3>;\n<min3>\nVUFD[0,32] = VUFS[0,32];\n<end3>\nif (VUFS[32,32] f< VUFT[32,32]) goto <min2>;\nVUFD[32,32] = VUFT[32,32];\ngoto <end2>;\n<min2>\nVUFD[32,32] = VUFS[32,32];\n<end2>\nif (VUFS[96,32] f< VUFT[96,32]) goto <min0>;\nVUFD[96,32] = VUFT[96,32];\ngoto <end0>;\n<min0>\nVUFD[96,32] = VUFS[96,32];\n<end0>\n");
        results.put(PcodeInjectLibraryVu.VMINBC, "if (VUFS[0,32] f< VUFT) goto <min3>;\nVUFD[0,32] = VUFT;\ngoto <end3>;\n<min3>\nVUFD[0,32] = VUFS[0,32];\n<end3>\nif (VUFS[32,32] f< VUFT) goto <min2>;\nVUFD[32,32] = VUFT;\ngoto <end2>;\n<min2>\nVUFD[32,32] = VUFS[32,32];\n<end2>\nif (VUFS[96,32] f< VUFT) goto <min0>;\nVUFD[96,32] = VUFT;\ngoto <end0>;\n<min0>\nVUFD[96,32] = VUFS[96,32];\n<end0>\n");
        results.put(PcodeInjectLibraryVu.VMFIR, "VUFT[0,32] = sext(VUIS);\nVUFT[32,32] = sext(VUIS);\nVUFT[96,32] = sext(VUIS);\n");
        results.put(PcodeInjectLibraryVu.VMOVE, "VUFT[0,32] = VUFS[0,32];\nVUFT[32,32] = VUFS[32,32];\nVUFT[96,32] = VUFS[96,32];\n");
        results.put(PcodeInjectLibraryVu.VMOVEBC, "VUFT[0,32] = VUFS;\nVUFT[32,32] = VUFS;\nVUFT[96,32] = VUFS;\n");
        results.put(PcodeInjectLibraryVu.VMR32, "VUFT[0,32] = VUFS[32,32];\nVUFT[32,32] = VUFS[64,32];\nVUFT[96,32] = VUFS[0,32];\n");
        results.put(PcodeInjectLibraryVu.VCLEAR, "VUFD[0,32] = int2float(0:4);\nVUFD[32,32] = int2float(0:4);\nVUFD[96,32] = int2float(0:4);\n");
        return Collections.unmodifiableMap(results);
    }
}
