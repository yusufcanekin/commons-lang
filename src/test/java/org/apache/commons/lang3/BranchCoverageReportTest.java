package org.apache.commons.lang3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class BranchCoverageReportTest {
    @AfterAll
    static void report() {
        BranchCoverage.report();
    }

    @Test
    void dummy() {
        // ensures this class runs
    }
}
