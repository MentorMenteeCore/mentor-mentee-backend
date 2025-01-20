import ws from 'k6/ws';
import { check, sleep } from 'k6';


export const options = {
    scenarios: {
        default: {
            executor: 'constant-vus',
            vus: 1,
            duration: '30s',
            gracefulStop: '60s', // 여기서 더 길게 준다. ex) '60s'
        },
    },
};

export default function () {
    const url = 'ws://localhost:8080/ws-stomp?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaG9lZ2lAZXhhbXBsZS5jb20iLCJyb2xlIjpbIlJPTEVfTUVOVEVFIl0sImlhdCI6MTczNzI2MjQ5MiwiZXhwIjoxNzM3MjY0MjkyfQ.uslrp8foAe6H7jRxCpkDrGajpCQkJLkkYbtFFj6HWkP-WnBtQWqgYHqo2sBM19jmUNN7RJdWzDd3n30FVYYkiA';

    // k6의 ws.connect로 웹소켓 연결 시도
    const res = ws.connect(url, {}, function (socket) {
        socket.on('open', () => {
            console.log('### WebSocket connected ###');

            let connectFrame =
                'CONNECT\n' +
                'accept-version:1.1,1.2\n' +
                'host:localhost\n' +
                'user-id:1\n' +
                '\n\x00';

            socket.send(connectFrame);
            console.log('>>> Sent STOMP CONNECT frame.');
        });

        // (2) 서버에서 오는 모든 메시지를 수신하여 STOMP 프레임 처리
        socket.on('message', (message) => {
            console.log(`<<< Received: ${message}`);

            // CONNECTED 프레임을 수신하면 SUBSCRIBE
            if (message.indexOf('CONNECTED') !== -1) {
                let subscribeFrame =
                    'SUBSCRIBE\n' +
                    'id:sub-0\n' +
                    'destination:/sub/chat/room/1/3\n' +
                    'user-id:1\n' +
                    '\n\x00';
                socket.send(subscribeFrame);
                console.log('>>> Sent SUBSCRIBE frame.');
            }
        });

        sleep(1);

        // STOMP SEND 프레임 (JSON 본문)
        let sendFrameHeader =
            'SEND\n' +
            'destination:/pub/chat/message\n' +
            'content-type:application/json\n' +
            '\n'; // 헤더 끝에는 빈 줄

        let sendFrameBody = JSON.stringify({
            roomId: '1/3',
            senderId: '1',
            message: '안녕하세요'
        });

        // STOMP 프레임은 끝에 \x00
        let stompSendFrame = sendFrameHeader + sendFrameBody + '\x00';

        // 1만 번 전송
        for (let i = 0; i < 10000; i++) {
            socket.send(stompSendFrame);
        }
        console.log(`>>> Sent 10000 messages`);

        // (4) 테스트 편의상 2초 정도 대기 후 소켓 닫기
        sleep(2);
        socket.close();
    });

    // 웹소켓 핸드셰이크(HTTP 101) 성공 여부 체크
    check(res, { 'status is 101': (r) => r && r.status === 101 });

    // k6 스크립트의 남은 시간(30초) 동안 이벤트 루프가 계속 돌면서
    // on('message') 콜백 등이 실행된다.
}