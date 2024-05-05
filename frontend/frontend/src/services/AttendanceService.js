import axios from 'axios';

const REST_API_BASE_URL = 'http://localhost:8080/api/attendance';

export const listAttendance = () => axios.get('http://vcm-38027.vm.duke.edu:8080/attendance', { headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } });