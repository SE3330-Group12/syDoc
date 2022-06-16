const useWebsockt = (docId,docType,username,handleMessage) => {
    const BASE_URL = '106.15.196.126';
    const WS_PORT = '8081';
    const DOC_ID = docId;
    const DOC_TYPE=docType;
    const USERNAME=username;
    const WS_ADDRESS = `ws://${BASE_URL}:${WS_PORT}/?docId=${DOC_ID}&docType=${DOC_TYPE}&username=${USERNAME}`;
    console.log("address", WS_ADDRESS)
    const ws = new WebSocket(WS_ADDRESS);
    /* const ws = new WebSocket("ws://localhost:8080"); */

    const init = () => {
        ws.addEventListener('open', handleOpen, false);
        ws.addEventListener('close', handleClose, false);
        ws.addEventListener('error', handleError, false);
        ws.addEventListener('message', handleMessage, false);
    };

    function handleOpen(e) {
        console.log('WebSocket open', e);
    }

    function handleClose(e) {
        console.log('WebSocket close', e);
    }

    function handleError(e) {
        console.log('WebSocket error', e);
    }

    init();
    return ws;
};

export default useWebsockt;