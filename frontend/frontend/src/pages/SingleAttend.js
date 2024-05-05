import EditAttendance from "../components/professor/EditAttendance";
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { listAttendance } from '../services/AttendanceService';
import axios from "axios";

function SingleAttend() {
  // test data
  const testData = [
    {
      "course_id": "651",
      "course_name": "Software Engineering",
      "section_id": "01",
      "attendance_records": [
        {
          "date": "2024-04-27",
          "students": [
            {
              "netid": "jd123",
              "full_name": "John Doe",
              "attendance_status": "Present"
            },
            {
              "netid": "js456",
              "full_name": "Jane Smith",
              "attendance_status": "Absent"
            }
          ]
        },
        {
          "date": "2024-04-28",
          "students": [
            {
              "netid": "jd123",
              "full_name": "John Doe",
              "attendance_status": "Absent"
            },
            {
              "netid": "aj789",
              "full_name": "Alice Johnson",
              "attendance_status": "Present"
            }
          ]
        }
      ]
    },
    {
      "course_id": "568",
      "course_name": "Engineering Robust Server Software",
      "section_id": "05",
      "attendance_records": [
        {
          "date": "2024-04-29",
          "students": [
            {
              "netid": "er234",
              "full_name": "Emily Rose",
              "attendance_status": "Present"
            },
            {
              "netid": "mb567",
              "full_name": "Michael Bay",
              "attendance_status": "Absent"
            }
          ]
        }
      ]
    }
  ]

  const { section_id, date } = useParams();
  const [attendanceStatus, setAttendanceStatus] = useState('');
  const [attendance, setAttendance] = useState([]);

  // useEffect(() => {
  //   axios.get('http://vcm-38027.vm.duke.edu:8080/attendance/', { headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } })
  //   .then((response) => {
  //     setAttendance(response.data);  
  //   }).catch(error => {
  //     console.error(error);
  //   });
  // }, []);

  useEffect(() => {
    listAttendance().then((response) => {
      console.log(section_id);
      console.log(response.data);
      setAttendance(response.data);
    }).catch(error => {
      console.error(error);
    })
  }, [])

  //TODO: put attendance status 
  // function updateStatus(status, student_id, section_id, date) {
  //   console.log( {
  //     new_status: status, student_id: student_id, section_id: section_id, date: date
  //   });
  //   axios.post(`http://localhost:8080/api/modifyAttendance/`, {
  //     new_status: status, student_id: student_id, section_id: section_id, date: date
  //   },  {headers:{"Authorization":`Bearer ${localStorage.getItem('token')}`}})
  //   .then(response => {
  //     console.log('Status updated successfully:', response.data);
  //   })
  //   .catch(error => {
  //     console.error('Failed to update status:', error);
  //   });
  // }

  return (
    <div className="pt-20 pl-80 pr-80">
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Date: {date}</h2>
      </div>

      <ul className="divide-y divide-gray-100">
        <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">Name</p>
            </div>
          </div>

          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">Attendance Status</p>
            </div>
          </div>

          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
            {/* keep the third column for format purpose */}
            </div>
          </div>
        </li>
        {attendance.map(course => (
          course.section_id === section_id && 
          course.attendance_records.map(rec => (
            //  
            rec.date === date && rec.students.map(stu => (
              <div>
              <li className="flex justify-between gap-x-6 py-5">
                <div className="flex min-w-0 gap-x-4">
                  <div className="min-w-0 flex-auto">
                    <p className="text-base font-semibold leading-6 text-gray-900">{stu.full_name}</p>
                  </div>
                </div>
    
                <div className="flex min-w-0 gap-x-4">
                  <div className="min-w-0 flex-auto">
                    <EditAttendance status={stu.attendance_status} student_id={stu.netid} section_id={course.section_id} date={rec.date}/>
                  </div>
                </div>
    
                {/* <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                  <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>updateStatus(attendanceStatus, stu.netid, course.section_id, rec.date)}>
                    Modify
                  </button>
                </div> */}
              </li>
            </div>
            ))
          ))
        )
        )}
      </ul>
    </div>
  )
}

export default SingleAttend;