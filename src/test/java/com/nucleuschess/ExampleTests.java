package com.nucleuschess;

import org.junit.jupiter.api.*;

//@Disabled
public final class ExampleTests {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before all tests.");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Before each test.");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After all tests.");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After each test.");
    }

    @Test
    public void test_text_is_hello_world() {
        final String text = "Hello World";

        Assertions.assertEquals("Hello World", text);
    }
}
