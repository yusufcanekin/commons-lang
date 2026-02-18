/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.lang3;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public final class BranchCoverage {
    private static final Set<String> ALL = ConcurrentHashMap.newKeySet();
    private static final Set<String> HIT = ConcurrentHashMap.newKeySet();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(BranchCoverage::report));
    }

    private BranchCoverage() {}

    public static void hit(final String id) {
        ALL.add(id);
        HIT.add(id);
    }

    public static void report() {
        final TreeSet<String> all = new TreeSet<>(ALL);
        int hit = 0;

        System.out.println("\n=== DIY BRANCH COVERAGE ===");

        java.io.File out = new java.io.File("target", "diy-coverage.txt");
        out.getParentFile().mkdirs();

        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(out, false))) {
            pw.println("=== DIY BRANCH COVERAGE ===");
            for (final String id : all) {
                final boolean ok = HIT.contains(id);
                if (ok) hit++;
                final String line = (ok ? "[HIT ] " : "[MISS] ") + id;
                System.out.println(line);
                pw.println(line);
            }
            final String summary = String.format("Hit %d/%d (%.1f%%)",
                    hit, all.size(), all.isEmpty() ? 100.0 : (hit * 100.0 / all.size()));
            System.out.println(summary);
            pw.println(summary);
        } catch (final java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

}
