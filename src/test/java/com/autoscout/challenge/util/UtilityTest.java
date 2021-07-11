package com.autoscout.challenge.util;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    public  void testPriceFormat(){
        assertEquals("€2323.33,-",Utility.priceFormat(2323.33));
    }

    @Test
    public  void testPercentageFormat(){
        assertEquals("223%",Utility.percentageFormat(223.33));
    }

    @Test
    public  void testMileageFormat(){
        assertEquals("12,33 KM",Utility.mileageFormat(12.33));
    }
}
