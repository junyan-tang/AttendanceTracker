import { useState } from 'react';
import { FaChevronDown } from "react-icons/fa6";
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const EditAttendance = ({status, student_id, section_id, date}) => {
  //console.log("status " + status);
  // setStatusToF(status.toLowerCase());
  const [dropdown, setDropdown] = useState(status.toLowerCase());

  const dropdownOptions = [
    {
      id: 1,
      label: "Present",
      value: "present",
    },

    {
      id: 2,
      label: "Absent",
      value: "absent",
    },

    {
      id: 3,
      label: "Tardy",
      value: "tardy",
    },

    {
      id: 4,
      label: "Excused",
      value: "excused",
    },
  ];

  const handleChange = (event) => {
    setDropdown(event.target.value.toLowerCase());
    // setStatusToF(event.target.value);
    console.log(event.target.value);
  };

  function updateStatus(status, student_id, section_id, date) {
    console.log( {
      new_status: status, student_id: student_id, section_id: section_id, date: date
    });
    axios.post('http://vcm-38027.vm.duke.edu:8080/attendance/modify', {
      new_status: status, student_id: student_id, section_id: section_id, date: date
    },  {headers:{"Authorization":`Bearer ${localStorage.getItem('token')}`}})
    .then(response => {
      toast.success('Status updated successfully');
      console.log('Status updated successfully:', response.data);
    })
    .catch(error => {
      console.error('Failed to update status:', error);
    });
  }


  return (
    <div>
    <ToastContainer />
    <div className="relative flex flex-col items-center w-[200px] h-[230px] rounded-lg">
      <select 
        value={dropdown} onChange={handleChange}
        className="bg-gray-200 p-1 w-full flex items-center justify-between dont-bold text-base rounded-lg tracking-wider border-4 border-transparent"
      >
        Choose Status
        <FaChevronDown className="-mr-1 h-5 w-5 text-gray-900" aria-hidden="true" />

        {dropdownOptions.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </div>

    <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
    <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500" onClick={()=>updateStatus(dropdown, student_id, section_id, date)}>
      Modify
    </button>
    </div>
</div>
  )
}

export default EditAttendance;