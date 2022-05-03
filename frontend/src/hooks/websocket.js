const useWebsockt = (docId, handleMessage) => {
    const BASE_URL = 'localhost';
    const WS_PORT = '8080';
    const DOC_ID = docId;
    const WS_ADDRESS = `ws://${BASE_URL}:${WS_PORT}/?name=${DOC_ID}`;
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