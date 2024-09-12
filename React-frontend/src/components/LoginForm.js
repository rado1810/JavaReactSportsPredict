import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginForm.css';  
import { useAuth } from './AuthContext'; 

const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { isAuthenticated, login } = useAuth();

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/Header');
    }
  }, [isAuthenticated, navigate]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const contentType = response.headers.get('Content-Type');
        let data;

        if (contentType && contentType.includes('application/json')) {
          data = await response.json();
          console.log(data)
          localStorage.setItem('username', data.username);
          localStorage.setItem('roles', JSON.stringify(data.roles));
          localStorage.setItem('token', data.token);
          login(data.username);
          
          
          navigate('/Header');
        } else {
          const text = await response.text();
          console.log('Login successful:', text);
          navigate('/Header');
        }
      } else {
        const errorMessage = await response.text();
        setError('Invalid username or password');
        console.log('Login failed:', errorMessage);
      }
    } catch (error) {
      console.error('Error during login:', error);
      setError('An error occurred during login');
    }
  };

  return (
    <div className="login-page">
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="form-input"
            />
          </div>
          <div className="form-group">
            <label>Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="form-input"
            />
          </div>
          <button type="submit" className="login-button">Login</button>
          {error && <p className="error-message">{error}</p>}
        </form>
      </div>
    </div>
    </div>
  );
};

export default LoginForm;
