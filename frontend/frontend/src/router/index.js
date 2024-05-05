import { Routes, Route, BrowserRouter } from 'react-router-dom';
import Login from '../pages/Login';
import Home from '../pages/Home';
import Courses from '../pages/Courses';
import Attendance from '../pages/Attendance';
import Report from '../pages/Report';
import Profile from '../pages/Profile';
import CourseAttend from '../pages/CourseAttend';
import SingleAttend from '../pages/SingleAttend';
import Checkin from '../pages/Checkin';
import NotLogin from '../pages/NotLogin';
import StuAttendRec from '../components/student/StuAttendRec';
import RequireAuth from '../components/RequireAuth';
import NotFound from '../pages/NotFound';

function MyRouter() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={
          <RequireAuth>
        <Home />
        </RequireAuth>
        } />
        <Route path="/login" element={<Login />} />
        <Route path="/courses" element={<RequireAuth><Courses /></RequireAuth>} />
        <Route path="/attendance" element={<RequireAuth><Attendance/></RequireAuth>} />
        <Route path="/report" element={<RequireAuth><Report /></RequireAuth>} />
        <Route path="/profile" element={<RequireAuth><Profile /></RequireAuth>} />
        <Route path="/checkin" element={<RequireAuth><Checkin /></RequireAuth>} />
        <Route path="/notlogin" element={<NotLogin/>} />
        <Route path="/attendance/:section_id/" element={<RequireAuth><CourseAttend /></RequireAuth>} />
        <Route path="/attendance/:section_id/:date" element={<RequireAuth><SingleAttend /></RequireAuth>} />
        <Route path="/attendanceHistory" element={<RequireAuth><StuAttendRec /></RequireAuth>} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  )
}

export default MyRouter;