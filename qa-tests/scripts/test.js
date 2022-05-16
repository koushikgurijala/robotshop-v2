// ./test.js
import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  duration: '1m',
  vus: 20,
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
  },
};

export default function () {
  const res = http.get('http://104.198.74.49:8080');
  sleep(1);
}
