import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import { 
  validateFirstName, 
  validateSecondName, 
  validateUsername, 
  validatePassword, 
  validateConfirmPassword, 
  validateEmail, 
  validateAge 
} from './Validation';
import './RegisterForm.css'; 

const RegisterForm = () => {
  const [firstName, setFirstName] = useState('');
  const [secondName, setSecondName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [age, setAge] = useState('');

  const navigate = useNavigate();
  const [firstNameError, setFirstNameError] = useState('');
  const [secondNameError, setSecondNameError] = useState('');
  const [usernameError, setUsernameError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [confirmPasswordError, setConfirmPasswordError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [ageError, setAgeError] = useState('');
  const [usernameExistsError, setUsernameExistsError] = useState('');

  const { isAuthenticated } = useAuth();
  
  useEffect(() => {
    if (isAuthenticated) {
      navigate('/Header');
    }
  }, [isAuthenticated, navigate]);

  const checkUsernameExists = async (usernameToCheck) => {
    try {
      const response = await fetch(`http://localhost:8080/api/userExist`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({  usernameToCheck }),
      });
      
      if (response.status === 409) {
        setUsernameExistsError('Username already exists');
        console.log("haha");
      } else {
        setUsernameExistsError('');
      }
    } catch (error) {
      console.error('Error checking username:', error);
    }
  };

  const handleFirstNameChange = (e) => {
    const value = e.target.value;
    setFirstName(value);
    setFirstNameError(validateFirstName(value));
  };

  const handleSecondNameChange = (e) => {
    const value = e.target.value;
    setSecondName(value);
    setSecondNameError(validateSecondName(value));
  };

  const handleUsernameChange = (e) => {
    const value = e.target.value;
    setUsername(value);
    setUsernameError(validateUsername(value));
    if (!validateUsername(value)) {
      checkUsernameExists(value);
    }
  };

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);
    setPasswordError(validatePassword(value));
    setConfirmPasswordError(validateConfirmPassword(value, confirmPassword));
  };

  const handleConfirmPasswordChange = (e) => {
    const value = e.target.value;
    setConfirmPassword(value);
    setConfirmPasswordError(validateConfirmPassword(password, value));
  };

  const handleEmailChange = (e) => {
    const value = e.target.value;
    setEmail(value);
    setEmailError(validateEmail(value));
  };

  const handleAgeChange = (e) => {
    const value = e.target.value;
    setAge(value);
    setAgeError(validateAge(value));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (
      !firstNameError &&
      !secondNameError &&
      !usernameError &&
      !passwordError &&
      !confirmPasswordError &&
      !emailError &&
      !ageError &&
      !usernameExistsError
    ) {
      const response = await fetch('http://localhost:8080/api/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ firstName, secondName, username, password, confirmPassword, email, age }),
      });

      if (response.ok) {
        console.log('Registration successful');
        navigate('/login');
      } else {
        console.log('Registration failed');
      }
    }
  };

  return (
    <div className="register-page">
      <div className="register-box">
        <h2>Register</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label>First Name:</label>
            <input
              type="text"
              value={firstName}
              onChange={handleFirstNameChange}
            />
            {firstNameError && <p>{firstNameError}</p>}
          </div>
          <div>
            <label>Second Name:</label>
            <input
              type="text"
              value={secondName}
              onChange={handleSecondNameChange}
            />
            {secondNameError && <p>{secondNameError}</p>}
          </div>
          <div>
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={handleUsernameChange}
              onBlur={() => checkUsernameExists(username)} 
            />
            {usernameError && <p>{usernameError}</p>}
            {usernameExistsError && <p>{usernameExistsError}</p>}
          </div>
          <div>
            <label>Password:</label>
            <input
              type="password"
              value={password}
              onChange={handlePasswordChange}
            />
            {passwordError && <p>{passwordError}</p>}
          </div>
          <div>
            <label>Confirm Password:</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={handleConfirmPasswordChange}
            />
            {confirmPasswordError && <p>{confirmPasswordError}</p>}
          </div>
          <div>
            <label>Email:</label>
            <input
              type="email"
              value={email}
              onChange={handleEmailChange}
            />
            {emailError && <p>{emailError}</p>}
          </div>
          <div>
            <label>Age:</label>
            <input
              type="number"
              value={age}
              onChange={handleAgeChange}
            />
            {ageError && <p>{ageError}</p>}
          </div>
          <button type="submit" className="button" >Register</button>
        </form>
      </div>
    </div>
  );
};
export default RegisterForm;
