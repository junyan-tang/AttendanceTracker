import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [netid, setNetid] = useState('');
  const [password, setPassword] = useState('');
  const navigate= useNavigate();
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        const response = await axios.post('http://vcm-38027.vm.duke.edu:8080/api/login', { netid, password });
        console.log("response: ", response);
        localStorage.setItem('token', response.data.accessToken);
        // const response_user = await axios.get('http://vcm-38027.vm.duke.edu:8080/profile', {headers:{"Authorization":`Bearer ${localStorage.getItem('token')}`}})
        // console.log("response_user: ", response_user);
        // await setFState({netid:response_user.data.netid, identity: response_user.data.identity});
        // console.log("fstate: ", fstate);
        navigate('/');
    } catch (error) {
        console.log("axios: ", error)
    }
};
  
  return (
<   div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">User Login</h2>
      </div>

      <div className="mt-6 sm:mx-auto sm:w-full sm:max-w-sm">
        <form className="space-y-6" action="#" method="POST" onSubmit={handleSubmit}>
          <div>
            <label for="netid" className="block text-sm font-medium leading-6 text-gray-900">NetID</label>
            <div className="mt-2">
              <input className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                id="netid"
                name="netid"
                type="text"
                value={netid}
                onChange={(e) => setNetid(e.target.value)}
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between">
              <label for="password" className="block text-sm font-medium leading-4 text-gray-900">Password</label>
            </div>
            <div className="mt-2">
              <input className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                id="password"
                name="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          <div>
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">
              Sign in
            </button>
          </div>
        </form>
      </div>
    </div> 
  )
}