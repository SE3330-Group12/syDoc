import javax.print.Doc;
import java.util.Scanner;

public class onMessage {
    String[] userNameMap = {"Johann", "hans", "ans"};
    String[] docIdMap = {"doc1", "doc2", "doc3"};
    String[] docTypeMap = {"doc", "sheet", "pic"};
    public static class WebSocket {
        int userNameIndex;
        int docIdIndex;
        int docTypeIndex;

        public WebSocket(int userNameIndex, int docIdIndex, int docTypeIndex) {
            this.userNameIndex = userNameIndex;
            this.docIdIndex = docIdIndex;
            this.docTypeIndex = docTypeIndex;
        }
    }
    WebSocket Johann = new WebSocket(0, 0, 0);
    WebSocket hans = new WebSocket(1, 1, 1);
    WebSocket ans = new WebSocket(2, 0, 0);
    WebSocket Johann1 = new WebSocket(0, 2, 2);

    public int judgeType(WebSocket conn, String message) {
        if (docTypeMap[conn.docTypeIndex].equals("doc")) {
            System.out.println(userNameMap[conn.userNameIndex] + " send to doc: " + docIdMap[conn.docIdIndex] + " message:" + message);
            return 0;
        } else if (docTypeMap[conn.docIdIndex].equals("sheet")) {
            System.out.println(userNameMap[conn.userNameIndex] + " send to doc: " + docIdMap[conn.docIdIndex] + " message:" + message);
            return 1;
        } else {
            System.out.println("wrong doc type");
            return -1;
        }
    }

    public int sendMessage(WebSocket conn, String message) {
//        connMap.forEach((socket,n)
        int id=conn.docIdIndex;
        int sends=0;
        if((conn!=Johann)&&(id==Johann.docIdIndex))
        {
//            send(message);
            System.out.println("send to Johann");
            sends++;
        }
        if((conn!=hans)&&(id==hans.docIdIndex))
        {
//            send(message);
            System.out.println("send to hans");
            sends++;
        }
        if((conn!=ans)&&(id==ans.docIdIndex))
        {
//            send(message);
            System.out.println("send to ans");
            sends++;
        }
        if(sends==0)
            System.out.println("no send");
        return sends;
    }
}