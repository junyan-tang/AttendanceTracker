import axios from 'axios';

// const REST_API_BASE_URL = 'http://localhost:8080/api/courses';

// const REST_API_BASE_URL = 'http://'

export const listCourses = () => axios.get('http://vcm-38027.vm.duke.edu:8080/courses', { headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } });