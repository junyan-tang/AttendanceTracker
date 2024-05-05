import React, { useEffect, useState } from 'react';
import { listCourses } from '../../services/CourseService';
import axios from 'axios';
// import { Navigate } from 'react-router-dom';
import { useNavigate } from 'react-router-dom'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function TakeAttendance({netid}) {
  const [section, setSection] = useState([])
  const [user, setUser] = useState(null)
  const [location, setLocation] = useState({ latitude: null, longitude: null });
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://vcm-38027.vm.duke.edu:8080/profile', { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
      setUser(response.data);
    }).catch(error => {
      console.error("error in profile" + error);
    })
  }, [])

  //fetch all sections for students here
  useEffect(() => {
    axios.get('http://vcm-38027.vm.duke.edu:8080/studentSections', { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
      console.log("response: "+response.data);
      console.log(JSON.stringify(response, null, 2));
      setSection(response.data);
    }).catch(error => {
      console.error("error in profile" + error);
      console.log(JSON.stringify(error, null, 2));
    })
  }, [])

  const takeAtt = (section_id, student_id)=>{
    console.log("section_id: "+section_id);
    console.log("student_id: "+student_id);
    if (!navigator.geolocation) {
      //setError('Geolocation is not supported by your browser');
      console.log('Geolocation is not supported by your browser');
  } else {
      navigator.geolocation.getCurrentPosition((position) => {
          setLocation({
              latitude: position.coords.latitude,
              longitude: position.coords.longitude
          })
          axios.post('http://vcm-38027.vm.duke.edu:8080/student/checkin', {sectionId: section_id, studentId: student_id, latitude: position.coords.latitude, longitude: position.coords.longitude}, { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
          console.log("response: "+response.data);  
          if(response.data==="Check-in successfully"){
              console.log("Check-in successful");
              navigate('/');
            }
            else{
              console.log("Check-in failed");
              }
          }).catch(error => {
            console.error("error in profile" + error);
          });
          // console.log("latitude: "+position.coords.latitude);
          // console.log("longitude: "+position.coords.longitude);
      }, () => {
          //setError('Unable to retrieve your location');
          console.log('Unable to retrieve your location');
      });
  }
  }

  const getReport = (section_id)=>{
    axios.post('http://vcm-38027.vm.duke.edu:8080/student/report', {sectionId: section_id}, {headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
      console.log("response: "+response.data);
      toast.success("Report generated successfully");
    }).catch(error => {
      console.error("error in profile" + error);
      toast.error("Error generating report");
    });
  }

  const goHistory = ()=>{
    navigate('/attendance');
  }

  if(!user||!section) {
    return <div>Fetching data</div>;
  }
  
  return (
    <div className="pt-20 pl-80 pr-80">
      <ToastContainer />
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Course List</h2>
      </div>

      <ul className="divide-y divide-gray-100">
        {section.map((sec) => (
          <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">{sec.course_name}</p>
              <p className="mt-1 truncate text-sm leading-5 text-gray-500">{sec.section_id}</p>
              {/* <p className="mt-1 truncate text-sm leading-5 text-gray-500">Software Engineering</p> */}
            </div>
          </div>

          <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>takeAtt(sec.section_id, user.netid)}>
              Take Attendance
            </button>
          </div>

          <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>goHistory()}>
              Attendance History
            </button>
          </div>

          {/* <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>getReport(sec.section_id)}>
              Generate Report
            </button>
          </div> */}

        </li>

        ))}
      </ul>
    </div>
  )
}

export default TakeAttendance;