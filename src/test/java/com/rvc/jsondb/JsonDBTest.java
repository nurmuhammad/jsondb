package com.rvc.jsondb;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Nurmuhammad
 *
 */
public class JsonDBTest {

    @Test
    public void save() throws IOException {

        JsonDB jsonDB = new JsonDB(System.getProperty("java.io.tmpdir") + "jsondb");

        BaseObject baseObject = new BaseObject("book");

        baseObject.put("name", "Design Patterns: Elements of Reusable Object-Oriented Software");
        baseObject.put("author", "Erich Gamma");
        baseObject.put("price", 49);
        baseObject.put("weight", .25);
        baseObject.put("e-book", false);
        baseObject.put("language", "english");

        baseObject.save(jsonDB);

        String id = baseObject.getId();

        Path path = Paths.get(System.getProperty("java.io.tmpdir"), "jsondb", "book", id + ".json");

        assertTrue(Files.exists(path, LinkOption.NOFOLLOW_LINKS));

        BaseObject baseObject2 = jsonDB.get("book", id);

        assertEquals(baseObject2.get("price").getAsInt(), 49);
        assertEquals(baseObject2.get("language").getAsString(), "english");

        assertEquals(baseObject.getJsonObject(), baseObject2.getJsonObject());

    }


}
