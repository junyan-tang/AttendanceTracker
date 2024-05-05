// import React, { useState, useEffect } from 'react';
// import { useNavigate, useParams } from 'react-router-dom';


// const ShowAtt({attendance})=>{

//   let navigate = useNavigate(); 
//   const routeChange = (section_id) =>  { 
//     let path = `${section_id}`; 
//     navigate(path);
//   }


//   return(
//     <div className="pt-20 pl-80 pr-80">
//     <div>
//       <h2 className="mt-10 text-2xl font-bold text-gray-900">Attendance History</h2>
//     </div>

//     <ul className="divide-y divide-gray-100">
//       {attendance.map(course => (
//         // {course.attendance_records.map(record => ())
//         <div>
//         <li className="flex justify-between gap-x-6 py-5">
//           <div className="flex min-w-0 gap-x-4">
//             <div className="min-w-0 flex-auto">
//               <p className="text-base font-semibold leading-6 text-gray-900">{course.course_id}</p>
//               <p className="text-base font-semibold leading-6 text-gray-900">{course.section_id}</p>
//               <p className="mt-1 truncate text-sm leading-5 text-gray-500">{course.course_name}</p>
//             </div>
//           </div>

//           <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
//             <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>routeChange(course.section_id)}>
//               Detail
//             </button>
//           </div>
//         </li>
//         </div>

//       ))}
//     </ul>
//   </div>
//   )
// }