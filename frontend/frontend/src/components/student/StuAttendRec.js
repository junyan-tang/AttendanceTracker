import React, { useEffect, useState } from 'react';
import { listCourses } from '../../services/CourseService';
import axios from 'axios';
// import { Navigate } from 'react-router-dom';
import { useNavigate } from 'react-router-dom'

function StuAttendRec() {
  const [section, setSection] = useState([])

  useEffect(() => {
    axios.get('http://vcm-38027.vm.duke.edu:8080/studentSections', { headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).then((response) => {
      console.log("response: " + response.data);
      console.log(JSON.stringify(response, null, 2));
      setSection(response.data);
    }).catch(error => {
      console.error("error in profile" + error);
      console.log(JSON.stringify(error, null, 2));
    })
  }, [])

  return (
    <div className="pt-20 pl-80 pr-80">
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Attendance History</h2>
      </div>

      <ul role="list" className="divide-y divide-gray-100">
        {section.map((sec) => (
          <li className="flex justify-between gap-x-6 py-5">
            <div className="flex min-w-0 gap-x-4">
              <div className="min-w-0 flex-auto">
                <p className="text-base font-semibold leading-6 text-gray-900">{sec.course_name}</p>
                <p className="mt-1 truncate text-sm leading-5 text-gray-500">{sec.section_id}</p>
                <ul>
                  {sec.attendance_records.map((rec) => (
                    <li className="flex justify-between gap-x-6 py-5">
                      <div className="flex min-w-0 gap-x-4">
                        <div className="min-w-0 flex-auto">
                          <p className="text-base font-semibold leading-6 text-gray-900">Date: {rec.date}</p>
                          {/* <p className="mt-1 truncate text-sm leading-5 text-gray-500"></p> */}
                          <p className="mt-1 truncate text-sm leading-5 text-gray-500">Status: {rec.status}</p>
                        </div>
                      </div>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </li>
        ))

        }
      </ul>
    </div>
  )
}

export default StuAttendRec;