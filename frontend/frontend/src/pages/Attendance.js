import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { listAttendance } from '../services/AttendanceService';
import StuAttendRec from '../components/student/StuAttendRec';
import axios from "axios";


function Attendance() {

  const { section_id, date } = useParams();
  const [attendance, setAttendance] = useState([])
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    listAttendance().then((response) => {
      console.log(response.data);
      setAttendance(response.data);
    }).catch(error => {
      console.error(error);
    })
  }, [])

  useEffect(() => {
    //curUser()
    axios.get('http://vcm-38027.vm.duke.edu:8080/profile', { headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } })
    .then((response) => {
      console.log("profile" + response);
      setUser(response.data);
    }).catch(error => {
      console.error("error in profile"+ error);
    })
  }, [])

  if (!attendance || !user) {
    return <div>Fetching data</div>;
  }

 
  const routeChange = (section_id) =>  { 
    let path = `${section_id}`; 
    navigate(path);
  }
  
  if(user.identity === "student"){
    navigate('/attendanceHistory')
  }

  // if(user.identity === "professor"){
  return (
    <div className="pt-20 pl-80 pr-80">
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Attendance History</h2>
      </div>

      <ul className="divide-y divide-gray-100">
        {attendance.map(course => (
          // {course.attendance_records.map(record => ())
          <div>
          <li className="flex justify-between gap-x-6 py-5">
            <div className="flex min-w-0 gap-x-4">
              <div className="min-w-0 flex-auto">
                <p className="text-base font-semibold leading-6 text-gray-900">{course.course_id}</p>
                <p className="text-base font-semibold leading-6 text-gray-900">{course.section_id}</p>
                <p className="mt-1 truncate text-sm leading-5 text-gray-500">{course.course_name}</p>
              </div>
            </div>
  
            <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
              <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>routeChange(course.section_id)}>
                Detail
              </button>
            </div>
          </li>
          </div>

        ))}
      </ul>
    </div>
  )
// }else{
//   return (
//     <StuAttendRec />
//   )
// }
}

export default Attendance;