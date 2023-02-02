package ru.gb.veber.testapplicationmockk

import io.mockk.*
import org.junit.Test
import java.lang.RuntimeException

class TwoSlotTest {
    private val dataRepo: DataRepo = mockk()

    private val kzCheck = KzCheck(dataRepo)

    @Test
    fun method1Easy() {
        val value1 = "value1"
        val value2 = "value2"
        every { dataRepo.setData(any(), any()) } just runs
        kzCheck.setData(value1, value2)
        verify(exactly = 1) { dataRepo.setData(value1, value2) }
        confirmVerified(dataRepo)
    }

    @Test
    fun method2Slot() {
        val value1 = "value1"
        val value2 = "value2"
        val value1Slot = slot<String>()
        val value2Slot = slot<String>()

        every { dataRepo.setData(capture(value1Slot),capture(value2Slot)) } just runs
       // every { dataRepo.setData(capture(value1Slot),capture(value2Slot)) } throws (RuntimeException())
        kzCheck.setData(value1, value2)

        verify(exactly = 1) { dataRepo.setData(value1, value2) }
        confirmVerified(dataRepo)
    }
}


class DataRepo() {
    private var name = ""
    private var name2 = ""

    fun setData(value1: String, value2: String) {
        name = value1
        name2 = value2
    }
    fun getData(): Pair<String, String> = Pair(name,name2)
}

class KzCheck(private val dataRepo: DataRepo) {
    fun setData(name: String, name2: String) {
        dataRepo.setData(name, name2)
    }
}