import React, { useEffect, useState } from 'react';
import { curUser } from '../services/ProfileService';
import Notification from "../components/student/Notification";
import axios from 'axios';
// import axios from 'axios';

function Profile() {
  const [user, setUser] = useState(null)
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

  if (!user) {
      return <div>Please login to load your profile.</div>;
  }

  // Json data for test
  const testData =
    {
      "netid": "jd123",
      "full_name": "John Doe",
      "display_name": "John",
      "email": "john.doe@duke.edu",
      "identity": "student",
      "notification": true
    }

  return (
    <div>
      <div className="ml-auto mr-auto mt-32 w-1/2 2xl:w-1/2">
        <div className="flex-1 bg-white rounded-lg shadow-inner p-8">
          <h4 className="text-xl text-gray-900 font-bold">Personal Information</h4>
          <ul className="mt-2 text-gray-700">
            <li className="flex-row border-b py-3">
              <span className="font-bold w-24">Full Name: </span>
              <span className="text-gray-700">{user.fullName}</span>
            </li>
            <li className="flex-row border-b py-3">
              <span className="font-bold w-24">Display Name: </span>
              <span className="text-gray-700">{user.preferName}</span>
            </li>
            <li className="flex-row border-b py-3">
              <span className="font-bold w-24">Net ID: </span>
              <span className="text-gray-700">{user.netid}</span>
            </li>
            <li className="flex-row border-b py-3">
              <span className="font-bold w-24">Email: </span>
              <span className="text-gray-700">{user.email}</span>
            </li> 
            <li className="flex-row border-b py-3">
              <span className="font-bold w-24">identity: </span>
              <span className="text-gray-700">{user.identity}</span>
            </li> 
          </ul>
          <div className="mt-3 text-gray-700">
            <Notification cur_user={user}/>
          </div>
        </div>
      </div>


    </div>
  )
}
export default Profile;