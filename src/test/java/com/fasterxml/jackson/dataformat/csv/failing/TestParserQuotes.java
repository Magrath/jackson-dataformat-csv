package com.fasterxml.jackson.dataformat.csv.failing;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.dataformat.csv.*;

public class TestParserQuotes extends ModuleTestBase
{
    @JsonPropertyOrder({"s1", "s2", "s3"})
    protected static class ThreeString {
        public String s1, s2, s3;
    }
    
    /*
    /**********************************************************************
    /* Test methods
    /**********************************************************************
     */

    // For #19: need to handle spaces outside quotes, even if not trimming?
    public void testSimpleQuotesWithSpaces() throws Exception
    {
        CsvMapper mapper = mapperForCsv();
        CsvSchema schema = mapper.schemaFor(ThreeString.class);
        ThreeString result = mapper.reader(schema).withType(ThreeString.class).readValue(
                "\"abc\"  ,  \"def\",  \"gh\"  \n");

        /*
System.err.println(" 1st => ["+result.s1+"]");
System.err.println(" 2nd => ["+result.s2+"]");
System.err.println(" 3rd => ["+result.s3+"]");
*/

        // start by trailing space trimming (easiest one to work)
        assertEquals("abc", result.s1);
        // follow by leading space trimming
        assertEquals("def", result.s2);
        // and then both
        assertEquals("gh", result.s3);
    }
}
