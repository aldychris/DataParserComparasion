package com.github.aldychris.compare

import com.github.aldychris.compare.model.Media
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class ExtensionsKtTest {

    data class Customer(val name: String, val gender: Boolean, val age: Int)

    @Test
    fun `test Object to Map return correct Map Key and value`(){
        val mapObj = Media.SAMPLE_OBJ.asMap()
        assert(mapObj.containsKey(Media::param1.name))
        assert(mapObj.containsKey(Media::param2.name))
        assert(mapObj.containsKey(Media::param3.name))

        assertEquals(mapObj[Media::param1.name], 19465)
        assertEquals(mapObj[Media::param2.name], "Varius repressor saepe attrahendams boreas est. Azureus, dexter resistentia!")

        //to lazy to continue the rest

        val x = Customer("John Doe", true, 21)
        print(x.toString())
    }

    @Test(expected = Exception::class)
    fun `test Object to Map return exception`(){
        val objectToConvert = "randomstring"
        print(objectToConvert.asMap())
    }

    @Test
    fun `test String to Map success return Map key and value as string`(){
        val mapRes = Media.SAMPLE_OBJ.asMap().toString()
        val mapObj = mapRes.toMap()
        assert(mapObj.containsKey(Media::param1.name))
        assert(mapObj.containsKey(Media::param2.name))
        assert(mapObj.containsKey(Media::param3.name))

        assertEquals(mapObj[Media::param1.name], "19465")
        assertEquals(mapObj[Media::param2.name], "Varius repressor saepe attrahendams boreas est. Azureus, dexter resistentia!")
        //to lazy to continue the rest
    }

    @Test
    fun `test convert plain string to Map will return empty Map`(){
        val stringValue = "Just plain string"
        val mapObj = stringValue.toMap()
        assert(mapObj.isNullOrEmpty())
    }

}

