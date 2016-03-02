package com.github.ligangty.market.pos.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class MockTest {

    @Test
    public void test() {
        System.out.println(new Gson().toJson(null));
    }

}
