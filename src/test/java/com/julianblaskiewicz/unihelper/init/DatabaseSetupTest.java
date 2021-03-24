package com.julianblaskiewicz.unihelper.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseSetupTest {

    @Autowired
    LearningProviderDatabaseSetup databaseSetup;

    @Test
    void shouldParseLine() {
//        String input = new String("10008640","FALMOUTH UNIVERSITY","University College Falmouth","Falmouth, University College","","","Woodlane Campus","","Woodlane","Falmouth","TR11 4RH","http://www.falmouth.ac.uk/","http://en.wikipedia.org/wiki/University_College_Falmouth","","-5.070901","50.149168","180711","32196","E84FC550-A4CC-4B98-A6F9-D15A33829D83","0017");
//        String[] expected = {"10008640","FALMOUTH UNIVERSITY","University College Falmouth","Falmouth, University College","","","Woodlane Campus","","Woodlane","Falmouth","TR11 4RH","http://www.falmouth.ac.uk/","http://en.wikipedia.org/wiki/University_College_Falmouth","","-5.070901","50.149168","180711","32196","E84FC550-A4CC-4B98-A6F9-D15A33829D83","0017"};
//        String[] actual = databaseSetup.parseLine(input);
//        assertEquals(expected, actual);
    }
}