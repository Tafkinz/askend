import * as axios from 'axios';
import { AxiosInstance } from 'axios';

const Api: AxiosInstance = axios.default.create({baseURL: 'http://localhost:8080'});

export { Api };
