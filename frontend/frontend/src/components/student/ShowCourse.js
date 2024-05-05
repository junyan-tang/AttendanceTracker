function ChooseCourse() {

  return (
    <div className="pt-20 pl-80 pr-80">
      <div>
        <h1 className="mt-10 text-4xl font-bold text-gray-900">Welcome to Attendance Tracker App</h1>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Here are all the courses you take</h2>
      </div>

      <ul className="divide-y divide-gray-100">
        <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">ECE 651</p>
              <p className="mt-1 truncate text-sm leading-5 text-gray-500">Software Engineering</p>
            </div>
          </div>
        </li>
   
        <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">ECE 568</p>
              <p className="mt-1 truncate text-sm leading-5 text-gray-500">Engineering Robust Server Software</p>
            </div>
          </div> 
        </li>

        <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">ECE 653</p>
              <p className="mt-1 truncate text-sm leading-5 text-gray-500">Human-Centered Computing</p>
            </div>
          </div>
        </li>
      </ul>
    </div>
  )
}

export default ChooseCourse;