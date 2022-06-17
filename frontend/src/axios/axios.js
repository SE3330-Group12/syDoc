import axios from 'axios';
const instance = axios.create(
    {
        baseURL: 'http://localhost:8085',
        timeout: 5000,
        headers:{
            'Content-type':'application/x-www-form-urlencoded'
        }
    });
export {instance};