export const validateFirstName = (firstName) => {
    if (!firstName.trim()) {
      return 'First Name cannot be empty';
    }
    if (firstName.length < 3 || firstName.length > 10) {
      return 'First Name must be between 3 and 10 characters long';
    }
    return '';
  };
  
  export const validateSecondName = (secondName) => {
    if (!secondName.trim()) {
      return 'Second Name cannot be empty';
    }
    if (secondName.length < 3 || secondName.length > 10) {
      return 'Second Name must be between 3 and 10 characters long';
    }
    return '';
  };
  
  export const validateUsername = (username) => {
    if (!username.trim()) {
      return 'Username cannot be empty';
    }
    if (username.length < 4 || username.length > 10) {
      return 'Username must be between 4 and 10 characters long';
    }
    return '';
  };
  
  export const validatePassword = (password) => {
    if (!password.trim()) {
      return 'Password cannot be empty';
    }
    if (password.length < 4 || password.length > 50) {
      return 'Password must be between 4 and 10 characters long';
    }
    return '';
  };
  
  export const validateConfirmPassword = (password, confirmPassword) => {
    if (password !== confirmPassword) {
      return 'Passwords do not match';
    }
    return '';
  };
  
  export const validateEmail = (email) => {
    if (!email.trim()) {
      return 'Email cannot be empty';
    }
  
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      return 'Email should be valid';
    }
    return '';
  };
  
  export const validateAge = (age) => {
    if (!age) {
      return 'Age cannot be empty';
    }
    if (age < 18) {
      return 'Age must be at least 18';
    }
    return '';
  };
  export const validBet = (bet) => {
    if (!bet) {
      return 'Bet cannot be empty';
    }
    if (bet < 1) {
      return 'Bet must be at least 1';
    }
    return '';
  };