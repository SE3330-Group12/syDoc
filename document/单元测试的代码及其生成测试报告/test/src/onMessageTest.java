import static org.junit.jupiter.api.Assertions.*;

class onMessageTest {
    onMessage t = new onMessage();
    @org.junit.jupiter.api.Test
    void judgeType() {
        assertEquals(0,t.judgeType(t.Johann,"doc1 modification"));
        assertEquals(1,t.judgeType(t.hans,"doc2 modification"));
        assertEquals(0,t.judgeType(t.ans,"doc1 modification"));
        assertEquals(-1,t.judgeType(t.Johann1,"doc3 modification"));
    }

    @org.junit.jupiter.api.Test
    void sendMessage() {
        assertEquals(1,t.sendMessage(t.Johann,"doc1 modification"));
        assertEquals(0,t.sendMessage(t.hans,"doc2 modification"));
        assertEquals(1,t.sendMessage(t.ans,"doc1 modification"));
        t.Johann.docIdIndex=1;
        t.Johann.docTypeIndex=1;
        assertEquals(1,t.sendMessage(t.Johann,"doc2 modification"));
    }
}