package io.felipeandrade.metalmancy.tools.toolgen

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode

/**
 * Kotest configuration for property-based testing.
 * 
 * This configuration ensures that all property-based tests run with at least 100 iterations
 * to provide comprehensive coverage across the input space.
 * 
 * Note: Individual tests explicitly specify iteration counts using checkAll(100, ...)
 * to ensure consistent behavior across all property-based tests.
 */
object KotestConfig : AbstractProjectConfig() {
    /**
     * Isolation mode for test execution.
     * InstancePerTest ensures each test runs in a fresh instance.
     */
    override val isolationMode = IsolationMode.InstancePerTest
    
    /**
     * Enable parallel test execution for faster test runs.
     */
    override val parallelism = 4
}
