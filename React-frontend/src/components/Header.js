import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css'; 

const Header = () => {
  const username = localStorage.getItem('username');
  const roles = JSON.parse(localStorage.getItem('roles'));

  const handleLogout = () => {
    fetch('http://localhost:8080/api/logout', {
      method: 'POST',
      credentials: 'include',
    }).then(() => {
      localStorage.removeItem('username');
      localStorage.removeItem('roles');
      window.location.reload(); 
    });
  };

  return (
    <>
      <header>
        <nav>
          <ul>
            {username ? (
              <>
                <li>Welcome, {username}</li>
                {roles.includes('ROLE_ADMIN') && (
                  <li><Link to="/admin">Admin Dashboard</Link></li> 
                )}
                <li><Link to="/comment">Coment</Link></li>
                <li><Link to="/allComment">allComments</Link></li>
                <li><Link to="/url">url</Link></li>
                <li><Link to="/adminUrl">Admin</Link></li>
                <li><button onClick={handleLogout}>Logout</button></li>
              </>
            ) : (
              <>
                <li><Link to="/login">Login</Link></li>
                <li><Link to="/register">Register</Link></li>
              </>
            )}
          </ul>
        </nav>
      </header>

      {username && (
        <div className='prediction'>
          <Link to="/prediction">Predictions</Link>
        </div>
      )}
    </>
  );
};

export default Header;
