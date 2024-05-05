function ProfAttendRec() {

  return (
    <div className="pt-20 pl-80 pr-80">
      <div>
        <h2 className="mt-10 text-2xl font-bold text-gray-900">Attendance History</h2>
      </div>

      <ul role="list" className="divide-y divide-gray-100">
        <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">ECE 651</p>
              <p className="mt-1 truncate text-sm leading-5 text-gray-500">Software Engineering</p>
            </div>
          </div>

          <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">
              Detail
            </button>
          </div>
        </li>
   
        <li className="flex justify-between gap-x-6 py-5">
          <div className="flex min-w-0 gap-x-4">
            <div className="min-w-0 flex-auto">
              <p className="text-base font-semibold leading-6 text-gray-900">ECE 568</p>
              <p className="mt-1 truncate text-sm leading-5 text-gray-500">Engineering Robust Server Software</p>
            </div>
          </div>
          <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">
              Detail
            </button>
          </div>
        </li>
      </ul>
    </div>
  )
}

export default ProfAttendRec;