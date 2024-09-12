import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './components/HomePage';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import LogoutForm from './components/LogoutForm';
import Header from './components/Header';
import { AuthProvider } from './components/AuthContext';
import AdminPage from './components/AdminPage';
import PredictionDisplay from './components/PredictionDisplay';
import Comment from './components/Comment';
import AllComments from './components/AllComments';
import VideoUrlRegister from './components/VideoUrlRegister';
import AdminVideoList from './components/AdminVideoList';



function App() {
  return (
    <AuthProvider>
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/register" element={<RegisterForm />} />
          <Route path="/logout" element={<LogoutForm />} />
          <Route path="/Header" element={<Header />} />
          <Route path="/admin" element={<AdminPage />} />
          <Route path="/prediction" element={<PredictionDisplay />} />
          <Route path="/comment" element={<Comment />} />
          <Route path="/allcomment" element={<AllComments />} />
          <Route path="/url" element={<VideoUrlRegister />} />
          <Route path="/adminUrl" element={<AdminVideoList />} />
        </Routes>
      </div>
    </Router>
    </AuthProvider>
  );
}

export default App;
