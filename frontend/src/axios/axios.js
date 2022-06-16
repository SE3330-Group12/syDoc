import axios from 'axios';
const instance = axios.create(
    {
        baseURL: 'http://106.15.196.126:8080',
        timeout: 5000,
        headers:{
            'Content-type':'application/x-www-form-urlencoded'
        }
    });
export {instance};