import React, { useEffect, useState } from 'react';
import { listCourses } from '../../services/CourseService';
import axios from 'axios';
// import { Navigate } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function AddAttendance() {
  const [courses, setCourses] = useState([])
  const [location, setLocation] = useState({ latitude: null, longitude: null });
  const [error, setError] = useState(null);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    listCourses().then((response) => {
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

  if (!courses || !user) {
      return <div>Fetching data</div>;
  }

  // Json data for test
  // const testData = [
  //   {
  //     "course_id": "651",
  //     "course_name": "Software Engineering",
  //     "sections": [
  //       {
  //         "section_id": "01",
  //         "assigned_to_professor": true,
  //         "professor_id": "ab12"
  //       },
  //       {
  //         "section_id": "02",
  //         "assigned_to_professor": false,
  //         "professor_id": null
  //       },
  //       {
  //         "section_id": "03",
  //         "assigned_to_professor": true,
  //         "professor_id": "ef12"
  //       }
  //     ]
  //   },
  //   {
  //     "course_id": "568",
  //     "course_name": "Engineering Robust Server Software",
  //     "sections": [
  //       {
  //         "section_id": "04",
  //         "assigned_to_professor": false,
  //         "professor_id": null
  //       },
  //       {
  //         "section_id": "05",
  //         "assigned_to_professor": true,
  //         "professor_id": "ef12"
  //       },
  //       {
  //         "section_id": "06",
  //         "assigned_to_professor": false,
  //         "professor_id": null
  //       }
  //     ]
  //   }
  // ]

  const getReport = (section_id)=>{
    axios.post('http://vcm-38027.vm.duke.edu:8080/professor/report', {sectionId: section_id}, {headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` }}).then((res) => {
      console.log(res);
      toast.success('Report generated successfully');
    }
    ).catch((error) => {
      console.error(error);
      toast.error('Error generating report');
    });
  }

  const sendLocation = async (section_id)=>{
    console.log(section_id);
    if (!navigator.geolocation) {
        setError('Geolocation is not supported by your browser');
        console.log('Geolocation is not supported by your browser');
    } else {
      console.log('Locating…');
        navigator.geolocation.getCurrentPosition(
            (position) => {
              console.log(position.coords.latitude);
              console.log({headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` }, sectionId: section_id, latitude: position.coords.latitude, longitude: position.coords.longitude});
              axios.post('http://vcm-38027.vm.duke.edu:8080/professor/startCheckin', {sectionId: section_id, latitude: position.coords.latitude, longitude: position.coords.longitude}, {headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` }}).then((res) => {
                console.log(res);
                navigate('/checkin');
              }).catch((error) => {
                console.error(error);
              });
            },
            (error) => {
                setError('Unable to retrieve your location'+ error);
            }
        );
    }
  }



  const filteredCourses = courses.filter(course =>
    course.sections.some(section => section.assigned_to_professor && section.professor_id === user.netid)
  );

  const filteredSections = filteredCourses.reduce((sections, course) => {
    const filtered = course.sections.filter(section => section.assigned_to_professor && section.professor_id === user.netid);
    return sections.concat(filtered);
  }, []);

  return (
    <div className="pt-20 pl-80 pr-80">
      <ToastContainer />
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Course List</h2>
      </div>

      <ul className="divide-y divide-gray-200">
        {filteredCourses.map(course => (
          <li key={course.course_id} className="flex flex-col min-w-0 gap-x-6 py-2">
            <div>
              <p className="text-base font-semibold leading-6 text-gray-900 py-2">{course.course_id}</p>
              <p className="text-base font-semibold leading-6 text-gray-900 py-2">{course.course_name}</p>
            </div>
          {course.sections.map(section => (
            section.assigned_to_professor && section.professor_id === user.netid && (
            <li className="flex flex-col min-w-0 gap-x-6 py-2 justify-between gap-x-6">
              <div className="flex flex-row justify-between gap-x-6 py-2">
                <div className="min-w-0 flex-auto">
                  <p className="mt-1 truncate text-base leading-5 text-gray-500">section: {section.section_id}</p>
                </div>
                <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                  <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>sendLocation(section.section_id)}>
                    Add new attendance
                  </button>
                </div>
                <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                  <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>getReport(section.section_id)}>
                    Generate a report
                  </button>
                </div>
              </div>
            </li>)
          ))}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default AddAttendance;