import axios from 'axios'
import React, { useState } from 'react'
import { useEffect } from 'react'

const Notification = ({cur_user}) => {
  const [isChecked, setIsChecked] = useState(cur_user.notifychoice)
  // const [user, setUser] = useState(null)

  // useEffect(() => {
  //   //curUser()
  //   axios.get('http://vcm-38027.vm.duke.edu:8080/profile', { headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } })
  //   .then((response) => {
  //     console.log("profile" + JSON.stringify(response, null, 2));
  //     setUser(response.data);
  //   }).catch(error => {
  //     console.error("error in profile"+ error);
  //   })
  // }, [])

  const handleCheckboxChange = () => {
    setIsChecked(!isChecked)
    console.log('isChecked:', !isChecked)
    console.log('cur_user:', cur_user)
    console.log({
      notifychoice: !isChecked,
      email: cur_user.email,
      identity: cur_user.identity,
      netid: cur_user.netid,
      full_name: cur_user.full_name,
      preferName: cur_user.preferName
    })
    axios.post('http://vcm-38027.vm.duke.edu:8080/profile', {
      notifychoice: !isChecked,
      email: cur_user.email,
      identity: cur_user.identity,
      netid: cur_user.netid,
      full_name: cur_user.full_name,
      preferName: cur_user.preferName
    },{ headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } }).catch(error => {
      console.error("error in profile"+ error);
    });
  }

  return (
    <label className='inline-flex select-none items-center'>
    <input
      type='checkbox'
      name='autoSaver'
      className='sr-only'
      checked={isChecked}
      onChange={handleCheckboxChange}
    />
    <span
      className={`slider mr-3 flex h-[26px] w-[50px] items-center rounded-full p-1 duration-200 ${
      isChecked ? 'bg-[#818cf8]' : 'bg-[#CCCCCE]'
      }`}
    >
    <span
      className={`dot h-[18px] w-[18px] rounded-full bg-white duration-200 ${
      isChecked ? 'translate-x-6' : ''
      }`}
    >
    </span>
    </span>
    <span className='label flex items-center text-sm font-bold w-24'>
      Notification <span className='pl-1'> {isChecked ? 'On' : 'Off'} </span>
    </span>
  </label>
  )
}

export default Notification;