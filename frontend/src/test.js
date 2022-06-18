let end, start, i , j;
let websocket=new Array(10);
let endtime=[];
for (i = 0; i < 10; i++) {
    websocket[i] = new Array(10);
  for(j = 0;j < 10; j++){
    websocket[i][j]=new WebSocket("ws://localhost:8083/?docId="+i+"&docType=doc&username=nzy");;
    websocket[i][j].onmessage = function(event) {
        end = new Date();
        end=end.getTime();
    };
  }
}

start = new Date();
for (i = 0; i < 10; i++){
  for(j = 0;j < 10; j++){
    websocket[i][j].send("test");
  }
}


console.log(end-start.getTime());
//local 25 66
//aliyun 99


// console.log("Operation took " + (end.getTime() - start.getTime()) + " msec");