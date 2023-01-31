package ru.gb.veber.testapplicationmockk

import android.util.Log
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import ru.gb.veber.testapplicationmockk.util.ErrorHandler
import ru.gb.veber.testapplicationmockk.util.ResourceManager
import java.util.concurrent.Executor
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    interface Bar {
        fun print(n: Int): Boolean
        fun foo()
        fun getInt(): Int
        fun getBoolean(): Boolean
    }

    @RelaxedMockK
    lateinit var bar: Bar

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var testExecutor: Executor

    @MockK
    lateinit var testErrorHandler: ErrorHandler<String>

    @InjectMockKs
    lateinit var resourceManager: ResourceManager<String>

    @Test
    fun test() {
        val testExecutor = mockk<Executor>(relaxed = true)
        val testErrorHandler = mockk<ErrorHandler<String>>()
        val resourceManager = ResourceManager(testExecutor, testErrorHandler)
        val resourceManagerSpy = spyk(resourceManager)
        resourceManagerSpy.destroy()
        verify { resourceManagerSpy.destroy() }
    }

    @Test
    fun test2() {
        every { resourceManager.destroy() } just runs
        resourceManager.destroy()
        verify { resourceManager.destroy() }
    }

    @Test
    fun test3() {

        every { bar.foo() } answers { println("foo() method has been called") }
        //every { bar.foo() } just runs

        every { bar.getBoolean() } answers {
            Random.nextBoolean()
        }
        // every { bar.getBoolean() } returns true

        every { bar.getInt() } returns 42
        //every { bar.getInt() } returns 42 andThen 7 andThen 22


//        every { testExecutor.execute(any()) } answers {
//            firstArg<Runnable>().run()
//        }
        bar.foo()
        println("Bool:{${bar.getBoolean()}}")
        println("Int:{${bar.getInt()}}")
    }

    @Test
    fun test4() {
        every { bar.print(less(0)) } returns false // меньше нуля то false

        every { bar.print(more(10)) } answers {
            println(firstArg<Int>())
            true
        }
        println(bar.print(2))
        bar.print(11)


        every { bar.print(-1) } returns  true
        every { bar.print(eq(-1)) } returns  true
        println(bar.print(-1))
    }

    @Test
    fun test5() {
        val initSlot = slot<Int>()
        every { bar.print(capture(initSlot)) } returns true

        bar.print(123)
        println("Capture arg:{${initSlot.captured}}")
    }
}