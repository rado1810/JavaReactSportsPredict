import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import './AdminPage.css';  

const AdminPage = () => {
 
  const [predict, setPredict] = useState('');
  const [sport, setSport] = useState('');
  const [bet, setBet] = useState('');
  const [potentialWin, setPotentialWin] = useState('');
  const [error, setError] = useState('');
  const author = localStorage.getItem('username');

  
  const roles = JSON.parse(localStorage.getItem('roles')) || [];

  
  if (!roles.includes('ROLE_ADMIN')) {
    return <Navigate to="/not-authorized" />;
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    const token = localStorage.getItem('token');
    
    try {
      const response = await fetch('http://localhost:8080/api/admin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        credentials: 'include',
        body: JSON.stringify({
          predict,
          sport,
          bet: parseFloat(bet),
          author,
          potentialWin: parseFloat(potentialWin),
        }),
      });

      if (response.ok) {
        alert('Prediction saved successfully!');
       
        setPredict('');
        setBet('');
        setPotentialWin('');
      } else {
        const errorMessage = await response.text();
        setError(`Error: ${errorMessage}`);
      }
    } catch (error) {
      console.error('Error during prediction submission:', error);
      setError('An error occurred during submission');
    }
  };

  return (
    <div className="admin-page">
      <h1>Admin Dashboard</h1>
      <div className='admin-inscription'>
      <p>Welcome, Admin! Only you can see and edit this content.</p>
      </div>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Prediction:</label>
          <input
            type="text"
            value={predict}
            onChange={(e) => setPredict(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Sport:</label>
          <input
            type="text"
            value={sport}
            onChange={(e) => setSport(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Bet:</label>
          <input
            type="number"
            step="0.01"
            value={bet}
            onChange={(e) => setBet(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Potential Win:</label>
          <input
            type="number"
            step="0.01"
            value={potentialWin}
            onChange={(e) => setPotentialWin(e.target.value)}
            required
          />
        </div>
        <button type="submit">Save Prediction</button>
      </form>

      {error && <p className="error-message">{error}</p>}
    </div>
  );
};

export default AdminPage;
