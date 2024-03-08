import io.kotest.core.spec.style.FunSpec

abstract class BaseTest<TEST_CLASS> : FunSpec() {

    protected abstract val testComponent: TEST_CLASS
}