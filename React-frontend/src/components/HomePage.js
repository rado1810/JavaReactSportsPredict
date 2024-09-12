import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from './AuthContext';
import { useNavigate } from 'react-router-dom';
import './HomePage.css'; 

const HomePage = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/Header');
    }
  }, [isAuthenticated, navigate]);

  return (
    <div className="homepage">
      <h1>Welcome to My App</h1>
      <p>
        <Link to="/login">Login</Link>
      </p>
      <p>
        <Link to="/register">Register</Link>
      </p>
    </div>
  );
};

export default HomePage;
