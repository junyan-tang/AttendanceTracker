import React from "react";
import { Navigate, useLocation } from "react-router-dom";

function RequireAuth({ children }) {
  const location = useLocation();
  const isAuthenticated = localStorage.hasOwnProperty("token");

  if (!isAuthenticated) {
    // 用户未登录，重定向到登录页面，并在登录后返回之前的页面
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
}

export default RequireAuth;
