import React, { useEffect, useState } from 'react';
import { listCourses } from '../../services/CourseService';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function ChooseCourse() {
  const [courses, setCourses] = useState([])
  const [user, setUser] = useState(null)
  useEffect(() => {
    listCourses().then((response) => {
      console.log("response type:");
      console.log("response: "+ response);
      console.log("courses data:"+response.data);
      console.log("courses status:"+response.status);
      console.log("courses statusText:"+response.statusText);
      console.log("courses headers:"+response.headers);
      console.log("courses body:"+response.body);
      console.log(JSON.stringify(response, null, 2))
      setCourses(response.data);
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

  const getReport = ()=>{
    axios.post('http://vcm-38027.vm.duke.edu:8080/student/report',{}, {headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
      console.log("response: "+response.data);
      toast.success("Report generated successfully");
    }).catch(error => {
      console.error("error in profile" + error);
      toast.error("Error generating report");
    });
  }

  const getCourse = ()=>{
    listCourses().then((response) => {
      setCourses(response.data);
    }).catch(error => {
      console.error(error);
    })
  }

  const takeSection = (section_id, net_id)=>{
    console.log("section_id: "+section_id);
    // console.log("course_id: "+course_id);
    axios.post('http://vcm-38027.vm.duke.edu:8080/teachCourse', {section_id: section_id, netid: net_id}, { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
      console.log("response: "+response.data);
      getCourse();
    }).catch(error => {
      console.error("error in profile" + error);
      console.log(JSON.stringify(error, null, 2));
    });
  }

  if (!courses || !user) {
      return <div>Fetching data</div>;
  }

  return (
    <div className="pt-20 pl-80 pr-80">
      <ToastContainer />
      <div>
        <h1 className="mt-10 text-4xl font-bold text-gray-900">Welcome to Attendance Tracker App</h1>
        {user.identity==='professor'?<h2 className="mt-10 text-2xl font-bold text-gray-900">Please choose the course you want to teach</h2>:<h2 className="mt-10 text-2xl font-bold text-gray-900">All course table</h2>}
      </div>

      <ul className="divide-y divide-gray-200">
          {user.identity==='student'&&<div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>getReport()}>
              Generate Report
            </button>
          </div>}

        {courses.map(course => (
          course.course_id!==null&&<li key={course.course_id} className="flex flex-col min-w-0 gap-x-6 py-2">
            {/* first part in list: course id and course name */}
            <div>
              <p className="text-base font-semibold leading-6 text-gray-900 py-2">{course.course_id}</p>
              <p className="text-base font-semibold leading-6 text-gray-900 py-2">{course.course_name}</p>
            </div>
          {course.sections.map(section => (
            <li className="flex flex-col min-w-0 gap-x-6 py-2 justify-between gap-x-6">
              <div className="flex flex-row justify-between gap-x-6 py-2">
                  <div className="min-w-0 flex-auto">
                    <p className="mt-1 truncate text-base leading-5 text-gray-500">section: {section.section_id}</p>
                  </div>
                  {user.identity === 'professor' && <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                    <div>
                    {section.assigned_to_professor ? (user.netid === section.professor_id ?                       
                    <button type="submit" className="flex w-full justify-center rounded-md bg-gray-400 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-gray-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-gray-400">
                        Already assigned to you
                      </button>:
                      <button type="submit" className="flex w-full justify-center rounded-md bg-gray-400 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-gray-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-gray-400">
                        Assigned
                      </button>)
                      :
                      //TODO: post
                      <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>takeSection(section.section_id, user.netid)}>
                        Teach Course
                      </button>
                    }
                    </div>
                  </div>}
              </div>
            </li>
          ))}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default ChooseCourse;