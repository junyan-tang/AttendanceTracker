import React, { useEffect, useState } from 'react';
// import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function NotLogin() {
  const navigate = useNavigate();
  useEffect(() => {
    localStorage.clear();
    navigate('/login');
  },[])
  return (
    <div className="ml-auto mr-auto mt-32 2xl:w-full flex justify-center flex-col">
      <h2 className="mt-10 text-2xl font-bold ml-80 mt-50 text-gray-900">
        You are not logged in.
      </h2>
      <div className="mt-10 ml-80 mt-50">
        <a href="/login" className="flex justify-center w-1/6 rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">
          Go to login page
        </a>
      </div>
    </div>
  )
}

export default NotLogin;