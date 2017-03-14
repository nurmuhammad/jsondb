package com.rvc.jsondb;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Nurmuhammad
 *
 */
public class JsonDBTest {

    @Test
    public void save() throws IOException {
        JsonDB jsonDB = new JsonDB("b:\\28\\db");
        BaseObject baseObject = new BaseObject();
        baseObject.setCollection("test");
//        baseObject.setId("1489515754-268-7558");
        baseObject.put("name", "Nurmuhammad");
        baseObject.put("number", 1);
        baseObject.put("date", new Date());
        baseObject.put("part", 10.9);
        baseObject.put("bool", true);
        baseObject.put("lang", "java");
        baseObject.put("list", jsonDB.ids("test"));

        baseObject.save(jsonDB);

        jsonDB.ids("test").forEach(System.out::println);

        boolean d = jsonDB.contains("book", "10");

//        for (int i=0; i<=10; i++){
//            System.out.println(jsonDB.uniqId("book"));
//        }

        assertEquals(d, true);
    }
}
