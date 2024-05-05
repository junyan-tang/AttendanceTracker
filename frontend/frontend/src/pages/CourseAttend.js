import React, { useEffect, useState } from 'react';
import { listAttendance } from '../services/AttendanceService';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

function CourseAttend() {
  // test data
  // const testData = [
  //   {
  //     "course_id": "651",
  //     "course_name": "Software Engineering",
  //     "section_id": "01",
  //     "attendance_records": [
  //       {
  //         "date": "2024-04-27",
  //         "students": [
  //           {
  //             "netid": "jd123",
  //             "full_name": "John Doe",
  //             "attendance_status": "Present"
  //           },
  //           {
  //             "netid": "js456",
  //             "full_name": "Jane Smith",
  //             "attendance_status": "Absent"
  //           }
  //         ]
  //       },
  //       {
  //         "date": "2024-04-28",
  //         "students": [
  //           {
  //             "netid": "jd123",
  //             "full_name": "John Doe",
  //             "attendance_status": "Absent"
  //           },
  //           {
  //             "netid": "aj789",
  //             "full_name": "Alice Johnson",
  //             "attendance_status": "Present"
  //           }
  //         ]
  //       }
  //     ]
  //   },
  //   {
  //     "course_id": "568",
  //     "course_name": "Engineering Robust Server Software",
  //     "section_id": "05",
  //     "attendance_records": [
  //       {
  //         "date": "2024-04-29",
  //         "students": [
  //           {
  //             "netid": "er234",
  //             "full_name": "Emily Rose",
  //             "attendance_status": "Present"
  //           },
  //           {
  //             "netid": "mb567",
  //             "full_name": "Michael Bay",
  //             "attendance_status": "Absent"
  //           }
  //         ]
  //       }
  //     ]
  //   }
  // ]

  const { section_id, date } = useParams();
  const [attendance, setAttendance] = useState([]);
  const [attendanceDates, setAttendanceDates] = useState([]);

  useEffect(() => {
    listAttendance().then((response) => {
      console.log(response.data);
      setAttendance(response.data);
    }).catch(error => {
      console.error(error);
    })
  }, [])


  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       // const response = await axios.get(`/api/${sectionID}/${date}`);
  //       setAttendanceDates(response.data);
  //     } catch (error) {
  //       console.error('Failed to fetch data:', error);
  //     }
  //   };

  //   fetchData();
  // }, [date]);

  let navigate = useNavigate(); 
  const routeChange = (date) =>{ 
    let path = `${date}`; 
    navigate(path);
  }

  // console.log(testData[0].attendance_records[0].date)

  return (
    <div className="pt-20 pl-80 pr-80">
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Attendance History for {section_id} section</h2>
      </div>

      <ul className="divide-y divide-gray-100">
        {attendance.map((course,idx) =>
          course.attendance_records.map((rec)=>
            course.section_id === section_id && (
              <li className="flex justify-between gap-x-6 py-5">
              <div className="flex min-w-0 gap-x-4">
                <div className="min-w-0 flex-auto">
                  <p className="text-base font-semibold leading-6 text-gray-900">Date</p>
                  <p className="mt-1 truncate text-sm leading-5 text-gray-500">{rec.date}</p>
                </div>
              </div>
    
              <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>routeChange(rec.date)}>
                  Detail
                </button>
              </div>
            </li>
          )
          )
        )}
      </ul>
  </div>
  )
}

export default CourseAttend;