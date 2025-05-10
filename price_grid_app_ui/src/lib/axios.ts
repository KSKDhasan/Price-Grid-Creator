import axios from 'axios';
import state from '@/lib/state'

const axiosInstance = axios.create({
    baseURL: state.api,
    headers: {
        'Content-Type': 'application/json',
    },
});

export default axiosInstance;
