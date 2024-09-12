import React, { useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { useNavigate } from 'react-router-dom';
import './Comment.css'; 

const Comment = () => {
    const [textContent, setTextContent] = useState('');
    const { isAuthenticated } = useAuth();
   
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/login');
        }
    }, [isAuthenticated, navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!textContent) {
            alert('Please enter a comment.');
            return;
        }

        const token =localStorage.getItem("token");
        const  user  =localStorage.getItem("username");

        const commentDto = {
            author: user,
            created: new Date().toISOString(),
            textContent,
        };

        try {
            const response = await fetch('/api/comment', {
                
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify(commentDto),
            });

            if (response.ok) {
                alert('Comment created successfully!');
                setTextContent('');
            } else {
                const error = await response.text();
                alert(`Failed to create comment: ${error}`);
            }
        } catch (error) {
            console.error('Error creating comment:', error);
            alert('An error occurred while creating the comment.');
        }
    };

    return (
        <div className="comment-container">
            <h2>Leave a Comment</h2>
            <form className="comment-form" onSubmit={handleSubmit}>
                <div>
                    <textarea
                        value={textContent}
                        onChange={(e) => setTextContent(e.target.value)}
                        placeholder="Enter your comment here"
                        rows="4"
                        cols="50"
                    />
                </div>
                <button type="submit">Submit Comment</button>
            </form>
        </div>
    );
};

export default Comment;
