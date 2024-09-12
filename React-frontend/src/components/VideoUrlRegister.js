import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import './VideoUrlRegister.css'; // Импортиране на CSS файла

const VideoUrlRegister = () => {
    const [url, setUrl] = useState('');
    const [isConfirmedByAdmin, setIsConfirmedByAdmin] = useState(false);
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const token = localStorage.getItem('token'); 

    useEffect(() => {
        if (!isAuthenticated ) {
            navigate('/login');
        }
    }, [isAuthenticated, navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!url) {
            alert('Please enter a URL!');
            return;
        }

        try {
            const response = await fetch('api/url', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    url: url, 
                    confirmedByAdmin: isConfirmedByAdmin
                })
            });

            if (response.ok) {
                alert('URL registered successfully!');
                setUrl('');
                setIsConfirmedByAdmin(false);
            } else {
                const errorMessage = await response.text();
                alert(`Error: ${errorMessage}`);
            }
        } catch (error) {
            console.error('Error during submission:', error);
            alert('An error occurred while submitting the URL.');
        }
    };

    return (
        <div className="form-wrapper">
            <h2 className="form-title">Register YouTube URL</h2>
            <form onSubmit={handleSubmit} className="form">
                <div className="form-group">
                    <label htmlFor="url">Video URL:</label>
                    <input
                        id="url"
                        type="text"
                        value={url}
                        onChange={(e) => setUrl(e.target.value)}
                        placeholder="Enter YouTube video URL"
                        className="input"
                    />
                </div>
                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default VideoUrlRegister;
