import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const AdminVideoList = () => {
  const [videos, setVideos] = useState([]);
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const fetchVideos = useCallback(async () => {
    try {
      const response = await fetch('http://localhost:8080/api/adminUrl', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (response.ok) {
        const data = await response.json();
        setVideos(data);
      } else {
        console.error('Failed to fetch videos');
      }
    } catch (error) {
      console.error('Error fetching videos:', error);
    }
  }, [token]);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    } else {
      fetchVideos();
    }
  }, [isAuthenticated, navigate, fetchVideos]);

  const approveVideo = async (videoId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/${videoId}/approve`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        fetchVideos(); 
      } else {
        console.error('Failed to approve video');
      }
    } catch (error) {
      console.error('Error approving video:', error);
    }
  };

  const deleteVideo = async (videoId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/${videoId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        fetchVideos(); 
      } else {
        console.error('Failed to delete video');
      }
    } catch (error) {
      console.error('Error deleting video:', error);
    }
  };

  return (
    <div>
      <h2>Pending Videos</h2>
      <table>
        <thead>
          <tr>
            <th>Video Preview</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {videos.map(video => (
            <tr key={video.id}>
              <td>
                <iframe
                  width="200"
                  height="150"
                  src={`https://www.youtube.com/embed/${video.url.split('v=')[1]}`}
                  title="YouTube video player"
                  frameBorder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowFullScreen
                ></iframe>
              </td>
              <td>
                <button onClick={() => approveVideo(video.id)}>Approve</button>
                <button onClick={() => deleteVideo(video.id)}>Delete</button> {/* Нов бутон за изтриване */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminVideoList;
